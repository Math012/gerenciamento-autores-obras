package com.math012.autoresobras.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.business.service.ObraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ObraControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    ObraController controller;
    @Mock
    ObraService obraService;

    String url;
    String json;
    MockMvc mockMvc;
    ObraRequestDTO obraRequestDTO;
    ObraResponseDTO obraResponseDTO;
    List<ObraResponseDTO> obraResponseDTOList;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
        url = "/author/works";
        obraRequestDTO = ObraRequestDTOFixture.build("Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", null);
        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose ", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", null, null);
        obraResponseDTOList = List.of(obraResponseDTO);
        json = objectMapper.writeValueAsString(obraRequestDTO);
    }

    @Test
    void deveCriarObraComSucesso() throws Exception {
        when(obraService.createAuthorsWorks(obraRequestDTO)).thenReturn(obraResponseDTO);
        mockMvc.perform(post(url + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveListarTodasAsObrasAtravesDoNomeDoAutor() throws Exception {
        when(obraService.findAllAuthorsWorksByName("Franz Kafka")).thenReturn(obraResponseDTOList);
        mockMvc.perform(get(url + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("author", "Franz Kafka")
        ).andExpect(status().isOk());
    }

    @Test
    void deveAtualizarObraComSucesso() throws Exception {
        Long id = 1L;
        when(obraService.updateAuthorsWorks(id, obraRequestDTO)).thenReturn(obraResponseDTO);
        mockMvc.perform(put(url + "/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveDeletarObraComSucesso() throws Exception {
        Long id = 1L;
        doNothing().when(obraService).deleteAuthorsWorksById(id);
        mockMvc.perform(delete(url + "/delete/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}