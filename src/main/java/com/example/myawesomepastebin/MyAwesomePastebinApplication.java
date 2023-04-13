package com.example.myawesomepastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyAwesomePastebinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAwesomePastebinApplication.class, args);
    }

}
