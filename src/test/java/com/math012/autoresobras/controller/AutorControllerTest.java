package com.math012.autoresobras.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.request.AutorRequestDTOFixture;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.business.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AutorControllerTest {

    @InjectMocks
    AutorController controller;

    @Mock
    AutorService autorService;

    String url;
    String json;
    String birthDay;
    SimpleDateFormat formatter;
    AutorRequestDTO autorRequestDTO;
    AutorRequestDTO autorRequestDTOInvalido;
    AutorResponseDTO autorResponseDTO;
    List<AutorResponseDTO> autorResponseDTOList;
    List<ObraRequestDTO> obraRequestDTOList;
    List<ObraResponseDTO> obraResponseDTOList;
    ObraResponseDTO obraResponseDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void setup() throws JsonProcessingException, ParseException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
        birthDay = "02/07/1883";
        String published = "01/01/1915";
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        url = "/author";
        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", null, null);
        obraResponseDTOList = List.of(obraResponseDTO);
        obraRequestDTOList = List.of(new ObraRequestDTO("Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", null));
        autorRequestDTO = AutorRequestDTOFixture.build("Franz Kafka", "Masculino", "franz@kafka.com", null, "Tchéquia", "", obraRequestDTOList);
        autorRequestDTO = AutorRequestDTOFixture.build(null, "Masculino", "franz@kafka.com", null, "Tchéquia", "", obraRequestDTOList);

        autorResponseDTO = AutorResponseDTOFixture.build(null, "Franz Kafka", "Masculino", "franz@kafka.com", null, "Tchéquia", "", obraResponseDTOList);
        autorResponseDTOList = List.of(autorResponseDTO);
        json = objectMapper.writeValueAsString(autorRequestDTO);

    }

    @Test
    void deveCriarAutorComSucesso() throws Exception {
        when(autorService.createAuthor(autorRequestDTO)).thenReturn(autorResponseDTO);
        mockMvc.perform(post(url + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveFalharAoCriarAutorComBodyNulo() throws Exception {
        mockMvc.perform(post(url + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveEncontrarAutorAtravesDoNomeComSucesso() throws Exception {
        when(autorService.findAuthorByName("Franz Kafka")).thenReturn(autorResponseDTO);
        mockMvc.perform(get(url + "/find/name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("name", "Franz Kafka")
        ).andExpect(status().isOk());
    }

    @Test
    void deveRetornarTodosOsAutoresComSucesso() throws Exception {
        when(autorService.findAll()).thenReturn(autorResponseDTOList);
        mockMvc.perform(get(url + "/find/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void deveAtualizarAutorComSucesso() throws Exception {
        Long idAutor = 1L;
        when(autorService.updateAuthor(idAutor, autorRequestDTO)).thenReturn(autorResponseDTO);
        mockMvc.perform(put(url + "/update/{id}", idAutor)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveDeletarAutorComSucesso() throws Exception {
        Long idAutor = 1L;
        doNothing().when(autorService).deleteAuthorById(idAutor);
        mockMvc.perform(delete(url + "/delete/{id}", idAutor)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}