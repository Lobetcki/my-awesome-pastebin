package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import com.example.myawesomepastebin.service.ServicePaste;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerPasteTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ControllerPaste controllerPaste;
    @Autowired
    ServicePaste servicePaste;
    @Autowired
    RepositoryPaste repositoryPaste;

    Paste paste;

    @BeforeEach
    public void setUp() {
        paste = new Paste();
        paste.setUrl("/");
        paste.setTitle("Asd");
        paste.setBody("Qweert");
        paste.setStatus(Status.PUBLIC);
        paste.setDataExpired(Instant.now().plus(1, ChronoUnit.MINUTES));
        paste.setDataCreated(Instant.now());
    }

    @AfterEach
    public void tearDown(){
        repositoryPaste.deleteAll();
    }

    @Test
    void whenCreatePaste() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "test");
        jsonObject.put("body", "Test test");

//        ExpirationTime expirationTime = ExpirationTime.TEN_MIN;
//        Status status = Status.PUBLIC;

        MvcResult result = mockMvc.perform(post("/?expirationTime=TEN_MIN&status=PUBLIC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").isNotEmpty())
                .andExpect(jsonPath("$.url").isNumber())
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.body").value("Test test"))

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.url");

        mockMvc.perform(get("/" +  id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(id))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.body").value("Test test"));
    }
}
