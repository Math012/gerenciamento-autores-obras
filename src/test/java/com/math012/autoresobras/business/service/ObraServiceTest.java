package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.business.converter.ObraConverter;
import com.math012.autoresobras.business.converter.mapper.ObraMapper;
import com.math012.autoresobras.business.converter.update.ObraUpdateMapper;
import com.math012.autoresobras.infra.entity.ObraEntity;
import com.math012.autoresobras.infra.exceptions.api.InvalidFieldsException;
import com.math012.autoresobras.infra.exceptions.api.ResourceNotFoundException;
import com.math012.autoresobras.infra.repository.ObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObraServiceTest {

    @InjectMocks
    ObraService obraService;

    @Mock
    ObraRepository repository;

    @Mock
    ObraMapper converter;

    @Mock
    ObraConverter obraConverter;

    @Mock
    ObraUpdateMapper updateMapper;


    ObraResponseDTO obraResponseDTO;
    ObraResponseDTO obraResponseDTOAtualizado;
    ObraEntity obraEntity;
    ObraEntity obraEntityAtualizado;

    ObraRequestDTO obraRequestDTO;
    ObraRequestDTO obraRequestDTOAtualizado;

    List<ObraResponseDTO> obraResponseDTOList;
    List<ObraEntity> obraEntityList;

    @BeforeEach
    void setup() throws ParseException {
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        obraEntity = new ObraEntity(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraEntityAtualizado = new ObraEntity(null, "Metamorfose atualizado", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);

        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose ", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraResponseDTOAtualizado = ObraResponseDTOFixture.build(null, "Metamorfose atualizado", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraResponseDTOList = List.of(obraResponseDTO);
        obraRequestDTO = ObraRequestDTOFixture.build("Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published));
        obraRequestDTOAtualizado = ObraRequestDTOFixture.build("Metamorfose atualizado", null, null);

        obraEntityList = List.of(obraEntity);
    }

    @Test
    void deveListarTodasAsObrasAtravesDoNomeAutor() {
        when(repository.findObrasByAuthor("Franz Kafka")).thenReturn(Optional.of(obraEntityList));
        when(obraConverter.forObraResponseDTOListFromObraEntityListConverter(obraEntityList)).thenReturn(obraResponseDTOList);
        List<ObraResponseDTO> response = obraService.findAllAuthorsWorksByName("Franz Kafka");
        assertEquals(obraResponseDTOList, response);
    }

    @Test
    void deveFalharAoListarTodasAsObrasComNomeAutorInvalido() {
        String nome = "Lian";
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            obraService.findAllAuthorsWorksByName(nome);
        });
        assertEquals("Erro ao procurar o autor: nome " + nome + " inválido, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveCriarObraComSucesso() {
        when(converter.forObraEntityFromObraRequestDTO(obraRequestDTO)).thenReturn(obraEntity);
        when(repository.save(obraEntity)).thenReturn(obraEntity);
        when(converter.forObraResponseDTOFromObraEntity(obraEntity)).thenReturn(obraResponseDTO);
        ObraResponseDTO response = obraService.createAuthorsWorks(obraRequestDTO);
        assertEquals(obraResponseDTO, response);
    }

    @Test
    void deveFalharAoCriarObraComCampoInvalido() {
        obraRequestDTO.setNome("  ");
        obraRequestDTO.setDescricao("   ");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            obraService.createAuthorsWorks(obraRequestDTO);
        });
        assertEquals("Erro ao criar a obra: campos inválidos!", invalidFieldsException.getMessage());
    }

    @Test
    void deveAtualizarObraComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(obraEntity));
        when(updateMapper.updateAuthorsWorks(obraRequestDTOAtualizado, obraEntity)).thenReturn(obraEntityAtualizado);
        when(repository.save(obraEntityAtualizado)).thenReturn(obraEntityAtualizado);
        when(obraConverter.forObraResponseDTOFromObraEntityConverter(obraEntityAtualizado)).thenReturn(obraResponseDTOAtualizado);
        ObraResponseDTO response = obraService.updateAuthorsWorks(1L, obraRequestDTOAtualizado);
        assertEquals(obraResponseDTOAtualizado, response);
    }

    @Test
    void deveFalharAoAtualizarObraComIdInvalido() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            obraService.updateAuthorsWorks(32L, obraRequestDTOAtualizado);
        });
        assertEquals("Erro ao atualizar a obra: id " + 32L + ", não encontrado, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveDeletarObraComSucesso() {
        obraEntity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(obraEntity));
        doNothing().when(repository).deleteById(1L);
        obraService.deleteAuthorsWorksById(1L);
        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveFalharAoDeletarObraComIdInvalido() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            obraService.deleteAuthorsWorksById(32L);
        });
        assertEquals("Erro ao deletar a obra: id " + 32L + ", não encontrado, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveRetornarErroAoVerificarCampoNomeComValoresInvalidos() {
        obraRequestDTO.setNome("     ");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            obraService.verifyFields(obraRequestDTO);
        });
        assertEquals("Erro ao criar a obra: campos inválidos!", invalidFieldsException.getMessage());
    }

    @Test
    void deveRetornarErroAoVerificarCampoDescricaoComValoresInvalidos() {
        obraRequestDTO.setDescricao("     ");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            obraService.verifyFields(obraRequestDTO);
        });
        assertEquals("Erro ao criar a obra: campos inválidos!", invalidFieldsException.getMessage());
    }
}