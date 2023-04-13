package com.example.myawesomepastebin;

import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MyAwesomePastebinApplicationTests {

//    @Test
//    void contextLoads() {
//    }

    @Autowired
    RepositoryPaste repositoryPaste;

    @BeforeEach
    public void setUp(){

    }


}
