package com.example.data_processor.service;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class WordCountService {

    @Value("${spark.master.url:local}")
    private String sparkMaster;

    private static final String APP_NAME = "Sample Spark Application";

    public String countWords(String file) {
        return executeJob(file);
    }

    private String executeJob(String file) {

        Pattern pattern = Pattern.compile("\\s");

        SparkConf sparkConf = new SparkConf()
            .setAppName(APP_NAME)
            .setMaster(sparkMaster);

        SparkSession sparkSession = SparkSession.builder()
            .config(sparkConf)
            .getOrCreate();

        Dataset<String> fileContent = sparkSession.read().textFile(file);

        Dataset<String> words = fileContent
            .flatMap(s -> Arrays.asList(pattern.split(s)).iterator(), Encoders.STRING());

        Dataset<Tuple2<String, Integer>> mapToOne = words
            .map(s -> new Tuple2<>(s, 1), Encoders.tuple(Encoders.STRING(), Encoders.INT()))
            .filter(tuple -> !"".equals(tuple._1));

        Dataset<Tuple2<String, Integer>> reduceByMatchingWord = mapToOne
            .groupByKey(Tuple2::_1, Encoders.STRING())
            .reduceGroups((a, b) -> new Tuple2<>(a._1, (a._2 + b._2)))
            .map(Tuple2::_2, Encoders.tuple(Encoders.STRING(), Encoders.INT()));

        List<Tuple2<String, Integer>> result = reduceByMatchingWord.collectAsList();

        Collections.sort(result, Collections.reverseOrder(Comparator.comparing(Tuple2::_2, Integer::compareTo)));

        sparkSession.close();

        return result.toString();
    }
}
