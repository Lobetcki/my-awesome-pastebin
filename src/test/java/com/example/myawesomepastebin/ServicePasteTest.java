package com.example.myawesomepastebin;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import com.example.myawesomepastebin.service.ServicePaste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicePasteTest {

    @Mock
    private RepositoryPaste repositoryMock;
    @InjectMocks
    private ServicePaste servicePaste;

    private final Paste pasteTest = new Paste();

    @Test
    void whenCreatePaste_AddPaste(){
        PasteDTO paste = new PasteDTO();
        paste.setExpirationTime(ExpirationTime.TEN_MIN);
        paste.setTitle("Asd");
        paste.setBody("Qwwer sdgh ftjrtfj");
        paste.setStatus(Status.PUBLIC);

        when(repositoryMock.save(any(Paste.class))).thenReturn(pasteTest);
        servicePaste.createPaste(paste);
        verify(repositoryMock, only()).save(any(Paste.class));
    }

        @Test
    void whenGetPaste_GetPasteByUrl() {
        String url = "/";
        Paste paste = new Paste();
        paste.setUrl(url);
        paste.setTitle("Asd");
        paste.setBody("asd");
        paste.setStatus(Status.PUBLIC);
        paste.setDataCreated(Instant.now().minus(Duration.ofDays(1)));
        paste.setDataExpired(Instant.now().plus(Duration.ofDays(1)));

        when(repositoryMock.findByUrlAndDataExpiredIsAfter(eq(url), any(Instant.class)))
                .thenReturn(Optional.of(paste));

        PasteGetDTO pasteGetDTO = servicePaste.getPasteUrl(url);

        assertEquals(url, pasteGetDTO.getUrl());
        assertEquals(paste.getTitle(), pasteGetDTO.getTitle());
        assertEquals(paste.getBody(), pasteGetDTO.getBody());
    }
}