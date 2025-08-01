package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.request.AutorRequestDTOFixture;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.infra.entity.AutorEntity;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AutorMapperTest {

    AutorMapper autorMapper;
    AutorEntity autorEntity;
    AutorRequestDTO autorRequestDTO;
    AutorResponseDTO autorResponseDTO;
    ObraResponseDTO obraResponseDTO;

    List<AutorResponseDTO> autorResponseDTOList;
    List<ObraRequestDTO> obraRequestDTOList;
    List<ObraResponseDTO> obraResponseDTOList;
    List<ObraEntity> obraEntityList;
    List<AutorEntity> autorEntityList;


    @BeforeEach
    void setup() throws ParseException {
        autorMapper = Mappers.getMapper(AutorMapper.class);
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraRequestDTOList = List.of(new ObraRequestDTO("Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published)));
        obraEntityList = List.of(new ObraEntity(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null));
        obraResponseDTOList = List.of(obraResponseDTO);
        autorEntity = new AutorEntity(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraEntityList);
        autorEntityList = List.of(autorEntity);
        autorRequestDTO = AutorRequestDTOFixture.build("Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraRequestDTOList);
        autorResponseDTO = AutorResponseDTOFixture.build(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraResponseDTOList);
        autorResponseDTOList = List.of(autorResponseDTO);
    }

    @Test
    void deveConverterDeAutorRequestDTOParaAutorEntity() {
        AutorEntity response = autorMapper.forAuthorEntityFromAutorRequestDTO(autorRequestDTO);
        assertEquals(autorEntity, response);
    }

    @Test
    void deveConverterDeAutorEntityParaAutorResponseDTO() {
        AutorResponseDTO response = autorMapper.forAutorResponseDTOFromAutorEntity(autorEntity);
        assertEquals(autorResponseDTO, response);
    }

    @Test
    void deveConverterDeAutorEntityListParaAutorResponseDTOList() {
        List<AutorResponseDTO> response = autorMapper.forAutorResponseDTOListFrom(autorEntityList);
        assertEquals(autorResponseDTOList, response);
    }

    @Test
    void deveConverterDeAutorResponseDTOListParaAutorEntityList() {
        List<AutorEntity> response = autorMapper.forAutorEntityListFromAutorResponseDTOList(autorResponseDTOList);
        assertEquals(autorEntityList, response);
    }
}