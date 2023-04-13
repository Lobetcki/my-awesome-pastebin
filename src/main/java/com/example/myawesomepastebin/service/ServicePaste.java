package com.example.myawesomepastebin.service;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ServicePaste {

    private final RepositoryPaste repositoryPaste;

    public ServicePaste(RepositoryPaste repositoryPaste) {
        this.repositoryPaste = repositoryPaste;
    }

    public UrlDTO createPaste(PasteDTO pasteDTO) {
        Paste paste = new Paste();
//        paste.setDataDeletion(Instant.now().plus());
        repositoryPaste.save(paste);
        return null;
    }
}
