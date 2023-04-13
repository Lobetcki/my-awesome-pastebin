package com.example.myawesomepastebin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Paste {
    @Id
    private String url;
    private Instant dataDeletion;
    private String title;
    private String paste;
}
