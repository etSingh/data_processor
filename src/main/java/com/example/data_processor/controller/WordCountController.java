package com.example.data_processor.controller;

import com.example.data_processor.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCountController {

    @Autowired
    private WordCountService wordCountService;

    @PostMapping("/")
    public ResponseEntity<String> countWords(String fileName){
        return ResponseEntity
                .accepted()
                .body(wordCountService.countWords(fileName));
    }
}
