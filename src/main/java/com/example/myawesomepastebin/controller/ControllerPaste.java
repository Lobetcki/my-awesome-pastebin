package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.service.ServicePaste;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ControllerPaste {

    private final ServicePaste servicePaste;
    public ControllerPaste(ServicePaste servicePaste) {
        this.servicePaste = servicePaste;
    }

    @PostMapping
    public ResponseEntity<UrlDTO> createPaste(@RequestBody PasteDTO pasteDTO,
                                              @RequestParam("expirationTime") ExpirationTime expirationTime,
                                              @RequestParam("status") Status status){
        if (pasteDTO == null || pasteDTO.getBody() == null || pasteDTO.getBody().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(servicePaste.createPaste(pasteDTO, expirationTime, status));
    }

    @GetMapping("last_ten")
    public ResponseEntity<List<PasteGetDTO>> getLastTen(){
        return ResponseEntity.ok(servicePaste.getLastTen());
    }

    @GetMapping("{url}")
    public ResponseEntity<PasteGetDTO> getPaste(String url) throws PasteNotFoundException {
        PasteGetDTO pasteGetDTO = servicePaste.getPaste(url);
        return ResponseEntity.ok(pasteGetDTO);
    }

    @GetMapping("text")
    public ResponseEntity<List<PasteGetDTO>> pastesFoundByText(
            @RequestParam(required = false) String titleText,
            @RequestParam(required = false) String bodyText){
        return ResponseEntity.ok(servicePaste.pastesFoundByText(titleText, bodyText));
    }
}
