package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.InvalidParametersExeption;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePaste {

    private final RepositoryPaste repositoryPaste;

    public ServicePaste(RepositoryPaste repositoryPaste) {
        this.repositoryPaste = repositoryPaste;
    }

                    // создание уникального ключа с добавлением даты для полной уникальности
    public String generatedKey(){
        SecureRandom secureRandom = new SecureRandom();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = secureRandom.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return "http://my-awesome-pastebin.tld/" + sb + Instant.now();
    }

    public UrlDTO createPaste(PasteDTO pasteDTO) {
        try {
        if (pasteDTO.getBody().isBlank()) {
            throw new InvalidParametersExeption("No paste");
        }
        Paste paste = PasteDTO.toPaste(pasteDTO);
        paste.setUrl(generatedKey());
        paste.setDataCreated(Instant.now());
        repositoryPaste.save(paste);
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setUrl(paste.getUrl());
        return urlDTO;
        } catch (InvalidParametersExeption e){
            throw  new InvalidParametersExeption("Неверно введены данные");
        }
    }

    public List<PasteGetDTO> getLastTen() {
        return repositoryPaste
                .findTop10ByStatusAndDataExpiredIsAfterOrderByDataCreatedDesc(
                Status.PUBLIC, Instant.now())
                .stream()
                .map(PasteGetDTO::from)
                .collect(Collectors.toList());
    }

    public PasteGetDTO getPasteUrl(String url) {
        Paste paste = repositoryPaste
                .findByUrlAndDataExpiredIsAfter(url, Instant.now())
                .orElseThrow(PasteNotFoundException::new);
        return PasteGetDTO.from(paste);
    }

    public List<PasteGetDTO> pastesFoundByText(String titleText, String bodyText) {

        List<Paste> pastes = repositoryPaste
                .findAllByTitleContainsOrBodyContainsAndStatusAndDataExpiredIsAfter(
                        titleText, bodyText, Status.PUBLIC, Instant.now());

        return pastes.stream().map(PasteGetDTO::from).collect(Collectors.toList());
    }
}


