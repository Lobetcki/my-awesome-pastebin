package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.exception.PasteNotFoundException;
import com.example.myawesomepastebin.service.ServicePaste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContTes {

        @Mock
        private ServicePaste serviceMock;

        @InjectMocks
        private ControllerPaste controllerPaste;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void whenCreatePasteWithBlankBody() {
            PasteDTO pasteDTO = new PasteDTO();
            pasteDTO.setBody("");
            ResponseEntity<UrlDTO> response = controllerPaste.createPaste(pasteDTO);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(serviceMock, never()).createPaste(any());
        }

        @Test
        void whenCreatePasteWithNullBody() {
            PasteDTO pasteDTO = new PasteDTO();
            ResponseEntity<UrlDTO> response = controllerPaste.createPaste(pasteDTO);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            verify(serviceMock, never()).createPaste(any());
        }

        @Test
        void whenCreatePasteWithValidBody() {
            PasteDTO pasteDTO = new PasteDTO();
            pasteDTO.setBody("Test");
            UrlDTO urlDTO = new UrlDTO();
            urlDTO.setUrl("/");
            when(serviceMock.createPaste(any())).thenReturn(urlDTO);
            ResponseEntity<UrlDTO> response = controllerPaste.createPaste(pasteDTO);
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(urlDTO, response.getBody());
            verify(serviceMock, times(1)).createPaste(any());
        }

        @Test
        void whenGetLastTenPastes() {
            List<PasteGetDTO> pasteGetDTOS = new ArrayList<>();
            PasteGetDTO pasteGetDTO = new PasteGetDTO();
            pasteGetDTOS.add(pasteGetDTO);
            when(serviceMock.getLastTen()).thenReturn(pasteGetDTOS);
            ResponseEntity<List<PasteGetDTO>> response = controllerPaste.getLastTen();
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(pasteGetDTOS, response.getBody());
            verify(serviceMock, times(1)).getLastTen();
        }

        @Test
        void whenGetPasteByValidUrl() throws PasteNotFoundException {
            PasteGetDTO pasteGetDTO = new PasteGetDTO();
            when(serviceMock.getPasteUrl(any())).thenReturn(pasteGetDTO);
            ResponseEntity<PasteGetDTO> response = controllerPaste.getPaste("1234");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(pasteGetDTO, response.getBody());
            verify(serviceMock, times(1)).getPasteUrl(any());
        }

        @Test
        void whenGetPasteByInvalidUrl() throws PasteNotFoundException {
            when(serviceMock.getPasteUrl(any())).thenThrow(new PasteNotFoundException());
            Assertions.assertThrows(PasteNotFoundException.class, () -> controllerPaste.getPaste("1234"));
            verify(serviceMock, times(1)).getPasteUrl(any());
        }
}
