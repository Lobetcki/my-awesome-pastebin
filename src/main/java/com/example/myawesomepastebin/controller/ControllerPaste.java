package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.service.ServicePaste;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(servicePaste.createPaste(pasteDTO));
    }
}
