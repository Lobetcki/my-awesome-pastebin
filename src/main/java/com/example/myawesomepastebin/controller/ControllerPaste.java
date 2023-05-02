package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
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

    // Сreate Paste
    @PostMapping
    public ResponseEntity<UrlDTO> createPaste(@RequestBody PasteDTO pasteDTO)
    {
        if (pasteDTO.getBody() == null || pasteDTO.getBody().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(servicePaste.createPaste(pasteDTO));
    }

    // Last 10 pastes
    @GetMapping("last_ten")
    public ResponseEntity<List<PasteGetDTO>> getLastTen(){
        return ResponseEntity.ok(servicePaste.getLastTen());
    }

    // Get paste
    @GetMapping("url/{url}")
    public ResponseEntity<PasteGetDTO> getPaste(String url) throws PasteNotFoundException {
        PasteGetDTO pasteGetDTO = servicePaste.getPasteUrl(url);
        return ResponseEntity.ok(pasteGetDTO);
    }

    // search
    @GetMapping("text")
    public ResponseEntity<List<PasteGetDTO>> pastesFoundByText(
            @RequestParam(required = false) String titleText,
            @RequestParam(required = false) String bodyText){
        return ResponseEntity.ok(servicePaste.pastesFoundByText(titleText, bodyText));
    }
}
