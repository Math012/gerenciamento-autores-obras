package com.math012.autoresobras.business.converter;

import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.infra.entity.AutorEntity;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutorConverterTest {

    @InjectMocks
    private AutorConverter autorConverter;

    @Mock
    private ObraConverter obraConverter;

    AutorResponseDTO autorResponseDTO;
    ObraResponseDTO obraResponseDTO;
    List<ObraResponseDTO> obraResponseDTOList;
    AutorEntity autorEntity;
    List<ObraEntity> obraEntityList;
    List<AutorResponseDTO> autorResponseDTOList;
    List<AutorEntity> autorEntityList;

    @BeforeEach
    void setup() throws ParseException {
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        obraEntityList = List.of(new ObraEntity(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null));
        autorEntity = new AutorEntity(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraEntityList);
        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraResponseDTOList = List.of(obraResponseDTO);
        autorResponseDTO = AutorResponseDTOFixture.build(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraResponseDTOList);
        autorResponseDTOList = List.of(autorResponseDTO);
        autorEntityList = List.of(autorEntity);
    }

    @Test
    void deveConverterDeAutorEntityParaAutorResponseDTO() {
        when(obraConverter.forObraResponseDTOListFromObraEntityListConverter(obraEntityList)).thenReturn(obraResponseDTOList);
        AutorResponseDTO response = autorConverter.forAutorResponseDTOFromAutorEntityConverter(autorEntity);
        assertEquals(autorResponseDTO, response);
    }

    @Test
    void deveConverterDeObraEntityListParaAutorResponseDTOList() {
        when(obraConverter.forObraResponseDTOListFromObraEntityListConverter(obraEntityList)).thenReturn(obraResponseDTOList);
        List<AutorResponseDTO> response = autorConverter.forAutorResponseDTOListFromAutorEntityListConverter(autorEntityList);
        assertEquals(autorResponseDTOList, response);
    }
}