package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Paste;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasteDTO {


    private String title;
    private String paste;

    public static Paste toPaste(PasteDTO pasteDTO) {
        Paste paste = new Paste();
        paste.setTitle(pasteDTO.getTitle());
        paste.setPaste(pasteDTO.getPaste());
        return paste;
    }

}
