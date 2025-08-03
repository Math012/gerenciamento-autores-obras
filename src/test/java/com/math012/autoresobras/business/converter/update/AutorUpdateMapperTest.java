package com.math012.autoresobras.business.converter.update;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.request.AutorRequestDTOFixture;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
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

@ExtendWith({MockitoExtension.class})
public class AutorUpdateMapperTest {

    AutorUpdateMapper autorUpdateMapper;

    AutorEntity autorEntity;
    AutorEntity autorEntityAtualizado;
    AutorRequestDTO autorRequestDTOAtualizado;
    List<ObraEntity> obraEntityList;
    List<ObraRequestDTO> obraRequestDTOList;

    @BeforeEach
    void setup() throws ParseException {
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        obraRequestDTOList = List.of(new ObraRequestDTO("Metamorfose","A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.",formatter.parse(published)));
        obraEntityList = List.of(new ObraEntity(null,"Metamorfose","A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.",formatter.parse(published),null));

        autorUpdateMapper = Mappers.getMapper(AutorUpdateMapper.class);
        autorEntity = new AutorEntity(null,"Franz Kafka","Masculino","franz@kafka.com",formatter.parse(birthDay),"Tchéquia","",obraEntityList);
        autorRequestDTOAtualizado = AutorRequestDTOFixture.build("Franz Kafka atualizado",null,null,null,"Tchéquia atualizado",null,null);
        autorEntityAtualizado = new AutorEntity(null,"Franz Kafka atualizado","Masculino","franz@kafka.com",formatter.parse(birthDay),"Tchéquia atualizado","",obraEntityList);
    }

    @Test
    void deveFazerUpdateDeAutorEntity(){
        AutorEntity response = autorUpdateMapper.updateAuthor(autorRequestDTOAtualizado,autorEntity);
        assertEquals(autorEntityAtualizado,response);
    }
}