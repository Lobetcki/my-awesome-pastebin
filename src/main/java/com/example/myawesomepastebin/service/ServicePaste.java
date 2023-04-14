package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.InvalidParametersExeption;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServicePaste {

    private final RepositoryPaste repositoryPaste;

    public ServicePaste(RepositoryPaste repositoryPaste) {
        this.repositoryPaste = repositoryPaste;
    }

    public UrlDTO createPaste(PasteDTO pasteDTO, ExpirationTime expirationTime, Status status) {

        if (pasteDTO == null || pasteDTO.getBody() == null || pasteDTO.getBody().isBlank()) {
            throw new InvalidParametersExeption("No paste");
        }
        Paste paste = PasteDTO.toPaste(pasteDTO);

        paste.setUrl("http://my-awesome-pastebin.tld/" + UUID.randomUUID());
        if (expirationTime.equals(ExpirationTime.UNLIMITED)) {
            paste.setDataExpired(null);
        } else {
            paste.setDataExpired(Instant.now().plus(expirationTime.getTime(), expirationTime.getChronoUnit()));
        }
        paste.setDataCreated(Instant.now());
        paste.setStatus(status);
        repositoryPaste.save(paste);
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(paste.getUrl());
        return urlDTO;
    }

    public List<PasteGetDTO> getLastTen() {
        return repositoryPaste.findTop10ByAccessAndExpiredDateIsAfterOrderByCreatedDateDesc(Status.PUBLIC, Instant.now()).stream()
                .map(PasteGetDTO::from).collect(Collectors.toList());
    }

    public PasteGetDTO getPaste(String url) {
        Paste paste = repositoryPaste.findByUrlAndDataExpiredIsAfter(url, Instant.now()).orElseThrow(PasteNotFoundException::new);
        return PasteGetDTO.from(paste);
    }

    public List<PasteGetDTO> pastesFoundByText(String titleText, String bodyText) {

        List<Paste> pastes = repositoryPaste
                .findAllByTitleContainsIgnoreCaseOrBodyContainsIgnoreCaseAndStatusAndDataExpiredIsAfter(
                        titleText, bodyText, Status.PUBLIC, Instant.now());
        return pastes.stream().map(PasteGetDTO::from).collect(Collectors.toList());
    }
}
