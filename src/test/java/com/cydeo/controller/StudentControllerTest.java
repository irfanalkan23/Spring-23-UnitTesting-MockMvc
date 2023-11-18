package com.cydeo.controller;

import com.cydeo.entity.Student;
import com.cydeo.service.StudentService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean   //just mocking the injection that was injected in StudentController
    StudentService studentService;

    @Test
    void getStudent() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/student")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"id\": 0, " +
                        "\"firstName\": \"Mike\", " +
                        "\"lastName\": \"Smith\", " +
                        "\"age\": 20}"))
                .andReturn();

    }

    //this assertion is hard coded. proper way is in the ticketing app
    @Test
    void jsonAssert() throws JSONException {

        String expected = "{\"id\": 0, \"firstName\": \"Mike\", \"lastName\": \"Smith\"}";
        String actual = "{\"id\": 0, \"firstName\": \"Mike\", \"lastName\": \"Smith\", \"age\": 20}";

        //third parameter is "strict"; true or false. compares strictly or not.
        //false: compares available fields only.
        JSONAssert.assertEquals(expected, actual, false);

    }

    @Test
    void getStudent_service() throws Exception {

        //using the @MockBean studentService
        when(studentService.getStudent()).thenReturn(Arrays.asList(
                new Student("John", "Doe", 20),
                new Student("Tom", "Hanks", 50)
        ));

        mvc.perform(MockMvcRequestBuilders
                .get("/service/student")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\": 0, \"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 20},{\"id\": 0, \"firstName\": \"Tom\", \"lastName\": \"Hanks\", \"age\": 50}]"))
                //instead of creating json like this, we can use toJsonString() code as in ticketing-project-rest-testing-review project ProjectControllerTest class

                .andReturn();
    }
}
