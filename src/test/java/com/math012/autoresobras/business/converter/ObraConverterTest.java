package com.math012.autoresobras.business.converter;

import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ObraConverterTest {

    @InjectMocks
    ObraConverter obraConverter;

    ObraResponseDTO obraResponseDTO;
    ObraEntity obraEntity;
    List<ObraResponseDTO> obraResponseDTOList;
    List<ObraEntity> obraEntityList;

    @BeforeEach
    void setup() throws ParseException {
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraEntity = new ObraEntity(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraResponseDTOList = List.of(obraResponseDTO);
        obraEntityList = List.of(obraEntity);
    }

    @Test
    void deveConverterDeObraEntityParaObraResponseDTO() {
        ObraResponseDTO response = obraConverter.forObraResponseDTOFromObraEntityConverter(obraEntity);
        assertEquals(obraResponseDTO, response);
    }

    @Test
    void deveConverterDeObraResponseDTOListParaObraEntityList() {
        List<ObraResponseDTO> response = obraConverter.forObraResponseDTOListFromObraEntityListConverter(obraEntityList);
        assertEquals(obraResponseDTOList, response);
    }
}