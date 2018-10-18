package com.example.data_processor.controller;

import com.example.data_processor.service.WordCountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WordCountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WordCountController wordCountController;

    @MockBean
    private WordCountService wordCountService;

    @Test
    public void getWordCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/")
                .content(""))
                .andExpect(status().isAccepted());
    }

}