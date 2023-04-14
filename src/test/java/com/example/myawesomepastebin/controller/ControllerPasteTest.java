package com.example.myawesomepastebin.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test-containers")
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

//    @Container
//    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
//            .withUsername("postgres")
//            .withPassword("test");
//
//    @DynamicPropertySource
//    static void postgresProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }

    @BeforeEach
    public void setUp() {
        paste = new Paste();
        paste.setUrl("/");
        paste.setTitle("Asd");
        paste.setBody("Qweert");
        paste.setStatus(Status.PUBLIC);
        paste.setDataExpired(Instant.now().plus(1, ChronoUnit.MINUTES));
        paste.setDataCreated(Instant.now());
        repositoryPaste.save(paste);
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

        MvcResult result = mockMvc.perform(post("/?expirationTime=TEN_MIN&status=PUBLIC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").isNotEmpty())
                .andExpect(jsonPath("$.url").isString())
                .andReturn();

        String url = JsonPath.read(result.getResponse().getContentAsString(), "$.url");

        mockMvc.perform(get("/" +  url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value(url))
                .andExpect(jsonPath("$.title").value("test"))
                .andExpect(jsonPath("$.body").value("Test test"));
    }

    @Test
    void whenGetLastTen() throws Exception {
        mockMvc.perform(get("/last_ten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

}
