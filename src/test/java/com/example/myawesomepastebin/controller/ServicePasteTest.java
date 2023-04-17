package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.exception.InvalidParametersExeption;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import com.example.myawesomepastebin.service.ServicePaste;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicePasteTest {

    @Mock
    private RepositoryPaste repositoryMock;
    @InjectMocks
    private ServicePaste serviceMock;
    private final Paste pasteTest = new Paste();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        serviceMock = new ServicePaste(repositoryMock);
    }

    @Test
    public void whenCreatePasteWithValidData() {

        PasteDTO paste = new PasteDTO();
        paste.setTitle("Asd");
        paste.setBody("Qwwer sdgh ftjrtfj");
        paste.setExpirationTime(ExpirationTime.ONE_HOUR);
        paste.setStatus(Status.PUBLIC);

        when(repositoryMock.save(any(Paste.class))).thenReturn(pasteTest);
        serviceMock.createPaste(paste);
        verify(repositoryMock, only()).save(any(Paste.class));
    }

    @Test
    public void whenCreatePasteWithInvalidData() {
        PasteDTO pasteDTO = new PasteDTO();
        pasteDTO.setTitle("");
        pasteDTO.setBody("");
        pasteDTO.setStatus(Status.PUBLIC);
        InvalidParametersExeption exception = assertThrows(
                InvalidParametersExeption.class, () -> serviceMock.createPaste(pasteDTO));
        assertEquals("Неверно введены данные", exception.getMessage());
    }

    @Test
    public void whenGetLastTen() {
        List<Paste> pastaList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Paste pasta = new Paste();
            if (i < 3) {
                pasta.setStatus(Status.PUBLIC);
            } else {
                pasta.setStatus(Status.UNLISTED);
            }
            pastaList.add(pasta);
        }

        when(repositoryMock.findTop10ByStatusAndDataExpiredIsAfterOrderByDataCreatedDesc(eq(Status.PUBLIC), any(Instant.class)))
                .thenReturn(pastaList.subList(0, 5));
        List<PasteGetDTO> pastaGetDTOList = serviceMock.getLastTen();

        assertEquals(5, pastaGetDTOList.size());
    }

    @Test
    void whenGetPasteUrl() {
        String url = "http://my-awesome-pastebin.tld/1234567890";
        Instant now = Instant.now();
        Paste paste = new Paste();
        paste.setUrl(url);
        paste.setDataExpired(now.plusSeconds(60));
        when(repositoryMock.findByUrlAndDataExpiredIsAfter(eq(url), any()))
                .thenReturn(Optional.of(paste));

        ServicePaste service = new ServicePaste(repositoryMock);
        PasteGetDTO result = service.getPasteUrl(url);

        assertNotNull(result);
        assertEquals(url, result.getUrl());
        verify(repositoryMock).findByUrlAndDataExpiredIsAfter(eq(url), any());
    }

    @Test
    void whenGetPasteUrlNotFound() {
        String url = "http://my-awesome-pastebin.tld/1234567890";
        when(repositoryMock.findByUrlAndDataExpiredIsAfter(eq(url), any()))
                .thenReturn(Optional.empty());

        ServicePaste service = new ServicePaste(repositoryMock);

        assertThrows(PasteNotFoundException.class, () -> service.getPasteUrl(url));
        verify(repositoryMock).findByUrlAndDataExpiredIsAfter(eq(url), any());
    }

    @Test
    void whenPastesFoundByText() {
        String titleText = "asd";
        String bodyText = "Asd Asd";
        List<Paste> pastes = new ArrayList<>();
        Paste paste1 = new Paste();
        paste1.setUrl("1");
        paste1.setTitle(titleText);
        paste1.setBody("Body 1");
        paste1.setStatus(Status.PUBLIC);
        paste1.setDataExpired(Instant.now().plusSeconds(60));
        Paste paste2 = new Paste();
        paste2.setUrl("2");
        paste2.setTitle("Title 2");
        paste2.setBody(bodyText);
        paste2.setStatus(Status.PUBLIC);
        paste2.setDataExpired(Instant.now().plusSeconds(60));
        Paste paste3 = new Paste();
        paste3.setUrl("3");
        paste3.setTitle(titleText);
        paste3.setBody(bodyText);
        paste3.setStatus(Status.PUBLIC);
        paste3.setDataExpired(Instant.now().plusSeconds(60));
        pastes.add(paste1);
        pastes.add(paste2);
        pastes.add(paste3);

        when(repositoryMock
                .findAllByTitleContainsOrBodyContainsAndStatusAndDataExpiredIsAfter(
                        eq(titleText), eq(bodyText), eq(Status.PUBLIC), any()))
                .thenReturn(pastes);
        ServicePaste service = new ServicePaste(repositoryMock);
        List<PasteGetDTO> result = service.pastesFoundByText(titleText, bodyText);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(pastes.get(0).getUrl(), result.get(0).getUrl());
        assertEquals(pastes.get(1).getUrl(), result.get(1).getUrl());
        verify(repositoryMock).findAllByTitleContainsOrBodyContainsAndStatusAndDataExpiredIsAfter(
                eq(titleText), eq(bodyText), eq(Status.PUBLIC), any());
    }
}
