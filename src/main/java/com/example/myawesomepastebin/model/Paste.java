package com.example.myawesomepastebin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Paste {
    @Id
    private String url;
    private Instant dataExpired;
    private Instant dataCreated;
    private String title;
    private String paste;
    @Enumerated(EnumType.STRING)
    private Status status;
}
