package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Paste;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasteDTO {

//    private Instant dataCreated;
    private String title;
    private String paste;

    public Paste toPaste(PasteDTO pasteDTO) {
        Paste paste = new Paste();
        paste.setTitle(pasteDTO.getTitle());
        paste.setPaste(pasteDTO.getPaste());
        return paste;
    }

}
