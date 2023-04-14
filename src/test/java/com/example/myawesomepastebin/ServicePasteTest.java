package com.example.myawesomepastebin;

import com.example.myawesomepastebin.dto.PasteDTO;
import com.example.myawesomepastebin.dto.PasteGetDTO;
import com.example.myawesomepastebin.model.ExpirationTime;
import com.example.myawesomepastebin.model.Paste;
import com.example.myawesomepastebin.model.Status;
import com.example.myawesomepastebin.repozitory.RepositoryPaste;
import com.example.myawesomepastebin.repozitory.specificashion.PasteSpecificashion;
import com.example.myawesomepastebin.service.ServicePaste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicePasteTest {

    @Mock
    private RepositoryPaste repositoryMock;
    @InjectMocks
    private ServicePaste servicePaste;

    private final Paste pasteTest = new Paste();
//    @BeforeEach

    @Test
    void whenCreatePaste_AddPaste(){
        PasteDTO paste = new PasteDTO();
        paste.setTitle("Asd");
        paste.setBody("Qwwer sdgh ftjrtfj");

        when(repositoryMock.save(any(Paste.class))).thenReturn(pasteTest);
        servicePaste.createPaste(paste, ExpirationTime.ONE_HOUR, Status.PUBLIC);
        verify(repositoryMock, only()).save(any(Paste.class));
    }

    @Test
    void whenGetLastTen_GetTenPaste(){
        List list = new ArrayList(10);

        when(repositoryMock.fundLastTen()).thenReturn(list);
        Assertions.assertEquals(list.size(), servicePaste.getLastTen().size());
    }

    @Test
    void whenGetPaste_GetPasteByUrl() {
        pasteTest.setTitle("Asd");
        pasteTest.setUrl("/");
        when(repositoryMock.findById("/"))
                .thenReturn(Optional.of(pasteTest));
        PasteGetDTO pasteDTO = servicePaste.getPaste("/");
        Assertions.assertEquals("/", pasteDTO.getUrl());
    }

//    @Test
//    void whenPastesFoundByText_GetByText(){
//        List<Paste> list = new ArrayList<>(1);
//        pasteTest.setTitle("123");
//        pasteTest.setBody("789");
//        pasteTest.setStatus(Status.PUBLIC);
//
//        List<PasteGetDTO> pasteList = servicePaste.pastesFoundByText("1", "7");
//        when(repositoryMock.findAll(PasteSpecificashion.byTitle("1")
//                .and(PasteSpecificashion.byBody("7"))
//                .and(PasteSpecificashion.byStatus(Status.PUBLIC)))).thenReturn(list);
//        Assertions.assertEquals(list.size(), pasteList.size());
////                servicePaste.pastesFoundByText("1", "7").size());
//    }

}