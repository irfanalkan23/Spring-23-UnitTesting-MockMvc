package com.cydeo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//in WelcomeController, I don't need everything (all beans in the app), it just returns "welcome",
//so, we don't need to use @AutoConfigureMockMvc and @SpringBootTest
//if we only need beans inside one class (WelcomeController), we can use @WebMvcTest instead of @SpringBootTest
//@WebMvcTest includes @AutoConfigureMockMvc in it
@WebMvcTest(WelcomeController.class)
class WelcomeControllerTest {

    @Autowired
    private MockMvc mvc;

    //First way. NOT preferred!
    @Test
    void welcome() throws Exception {

        // call /welcome endpoint
        // verify "welcome"

        //create a request
        RequestBuilder request = MockMvcRequestBuilders
                .get("/welcome")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("welcome", result.getResponse().getContentAsString());

    }

    //Second way. Preferred.
    @Test
    void welcome2() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/welcome")
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("welcome"))
                .andReturn();
    }
}
