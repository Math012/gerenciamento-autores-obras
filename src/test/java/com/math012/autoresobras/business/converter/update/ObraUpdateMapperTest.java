package com.math012.autoresobras.business.converter.update;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTOFixture;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ObraUpdateMapperTest {

    ObraUpdateMapper obraUpdateMapper;

    ObraEntity obraEntity;
    ObraEntity obraEntityAtualizado;
    ObraRequestDTO obraRequestDTOAtualizado;

    @BeforeEach
    void setup() throws ParseException {
        obraUpdateMapper = Mappers.getMapper(ObraUpdateMapper.class);
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        obraEntity = new ObraEntity(null, "nome obra", "descricao obra", formatter.parse(published), null);
        obraRequestDTOAtualizado = ObraRequestDTOFixture.build("nome obra atualizado", null, null);
        obraEntityAtualizado = new ObraEntity(null, "nome obra atualizado", "descricao obra", formatter.parse(published), null);
    }

    @Test
    void deveFazerUpdateDeObraEntity() {
        ObraEntity response = obraUpdateMapper.updateAuthorsWorks(obraRequestDTOAtualizado, obraEntity);
        assertEquals(obraEntityAtualizado, response);

    }
}