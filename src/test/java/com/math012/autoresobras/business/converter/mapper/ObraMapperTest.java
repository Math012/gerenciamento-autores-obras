package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
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
public class ObraMapperTest {

    ObraMapper obraMapper;
    ObraEntity obraEntity;
    ObraRequestDTO obraRequestDTO;
    ObraResponseDTO obraResponseDTO;
    List<ObraResponseDTO> obraResponseDTOList;
    List<ObraEntity> obraEntityList;

    @BeforeEach
    void setup() throws ParseException {
        obraMapper = Mappers.getMapper(ObraMapper.class);
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        obraEntity = new ObraEntity(null, "nome obra", "descricao obra", formatter.parse(published), null);
        obraRequestDTO = ObraRequestDTOFixture.build("nome obra", "descricao obra", formatter.parse(published));
        obraResponseDTO = ObraResponseDTOFixture.build(null, "nome obra", "descricao obra", formatter.parse(published), null);
        obraResponseDTOList = List.of(obraResponseDTO);
        obraEntityList = List.of(obraEntity);
    }

    @Test
    void deveConverterDeObraRequestDTOParaObraEntity() {
        ObraEntity response = obraMapper.forObraEntityFromObraRequestDTO(obraRequestDTO);
        assertEquals(obraEntity, response);

    }

    @Test
    void deveConverterDeObraEntityParaObraResponseDTO() {
        ObraResponseDTO response = obraMapper.forObraResponseDTOFromObraEntity(obraEntity);
        assertEquals(obraResponseDTO, response);
    }

    @Test
    void deveConverterDeObraEntityListParaObraResponseDTOList() {
        List<ObraResponseDTO> response = obraMapper.forObraResponseDTOListFromObraEntityList(obraEntityList);
        assertEquals(obraResponseDTOList, response);
    }
}