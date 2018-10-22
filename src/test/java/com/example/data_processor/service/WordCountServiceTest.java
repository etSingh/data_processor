package com.example.data_processor.service;

import com.example.data_processor.DemoApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
public class WordCountServiceTest {

    @Autowired
    private WordCountService wordCountService;

    @Before
    public void setup() {
        System.setProperty("hadoop.home.dir", getClass()
                .getClassLoader()
                .getResource("hadoop")
                .getPath());
    }

    @Test
    public void shouldReturnCountOfWords() {
        Assertions.assertNotNull
                (wordCountService
                        .countWords(getClass()
                                .getClassLoader()
                                .getResource("files/caesar.txt")
                                .getPath()));
    }
}