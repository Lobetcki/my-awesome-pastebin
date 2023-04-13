package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
@Getter
@Setter
public class PasteGetDTO {

    private String url;
    private Instant dataExpired;
    private Instant dataCreated;
    private String title;
    private String paste;

    public static PasteGetDTO from(Paste paste) {
        PasteGetDTO pasteGetDTO = new PasteGetDTO();

        pasteGetDTO.setUrl(paste.getUrl());
        pasteGetDTO.setDataExpired(paste.getDataExpired());
        pasteGetDTO.setDataCreated(
        pasteGetDTO.setTitle(paste.getTitle());
        pasteGetDTO.setPaste(paste.getPaste());

        return pasteGetDTO;
    }
}
