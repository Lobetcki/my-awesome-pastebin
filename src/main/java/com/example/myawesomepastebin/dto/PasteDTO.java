package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

@Getter
@Setter
public class PasteDTO {

    private ExpirationTime expirationTime;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Paste toPaste(PasteDTO pasteDTO) {
        Paste paste = new Paste();
        if (pasteDTO.getExpirationTime().getTime() == null ||
                pasteDTO.getExpirationTime().getChronoUnit() == null){
            paste.setDataExpired(null);
        } else {
        paste.setDataExpired(Instant.now()
                .plus(pasteDTO.expirationTime.getTime(),
                        pasteDTO.expirationTime.getChronoUnit()));
        }
        paste.setTitle(pasteDTO.getTitle());
        paste.setBody(pasteDTO.getBody());
        paste.setStatus(pasteDTO.status);
        return paste;
    }
}
