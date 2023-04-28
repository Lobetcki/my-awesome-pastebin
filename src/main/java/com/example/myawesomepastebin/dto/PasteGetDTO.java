package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Paste;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class PasteGetDTO {

    private String url;
    private Instant dataExpired;
    private Instant dataCreated;
    private String title;
    private String body;

    public static PasteGetDTO from(Paste paste) {
        PasteGetDTO pasteGetDTO = new PasteGetDTO();

        pasteGetDTO.setUrl(paste.getUrl());
        pasteGetDTO.setDataExpired(paste.getDataExpired());
        pasteGetDTO.setDataCreated(Instant.now());
        pasteGetDTO.setTitle(paste.getTitle());
        pasteGetDTO.setBody(paste.getBody());

        return pasteGetDTO;
    }
}
