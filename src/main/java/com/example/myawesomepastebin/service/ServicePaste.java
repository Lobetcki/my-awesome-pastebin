package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServicePaste {

    private final RepositoryPaste repositoryPaste;

    public ServicePaste(RepositoryPaste repositoryPaste) {
        this.repositoryPaste = repositoryPaste;
    }

    public UrlDTO createPaste(PasteDTO pasteDTO, ExpirationTime expirationTime, Status status) {
        Paste paste = PasteDTO.toPaste(pasteDTO);
        paste.setUrl("http://my-awesome-pastebin.tld/" + UUID.randomUUID().toString().substring(0, 7));
        paste.setDataExpired(Instant.now().plus(expirationTime.getTime(), expirationTime.getChronoUnit()));
        paste.setDataCreated(Instant.now());
        paste.setStatus(status);
        repositoryPaste.save(paste);
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(paste.getUrl());
        return urlDTO;
    }

    public List<PasteGetDTO> getLastTen() {
        return repositoryPaste.fundLastTen().stream()
                .map(PasteGetDTO::from).collect(Collectors.toList());
    }

    public PasteGetDTO getPaste(String url) {
        Paste paste = repositoryPaste.findById(url).orElseThrow();
        return PasteGetDTO.from(paste);
    }
}
