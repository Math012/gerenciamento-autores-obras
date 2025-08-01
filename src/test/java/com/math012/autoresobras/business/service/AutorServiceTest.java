package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.request.AutorRequestDTOFixture;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTOFixture;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTOFixture;
import com.math012.autoresobras.business.converter.AutorConverter;
import com.math012.autoresobras.business.converter.mapper.AutorMapper;
import com.math012.autoresobras.business.converter.mapper.ObraMapper;
import com.math012.autoresobras.business.converter.update.AutorUpdateMapper;
import com.math012.autoresobras.infra.entity.AutorEntity;
import com.math012.autoresobras.infra.entity.ObraEntity;
import com.math012.autoresobras.infra.exceptions.api.CpfException;
import com.math012.autoresobras.infra.exceptions.api.InvalidFieldsException;
import com.math012.autoresobras.infra.exceptions.api.ResourceNotFoundException;
import com.math012.autoresobras.infra.repository.AutorRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @InjectMocks
    AutorService autorService;

    @Mock
    AutorRepository repository;
    @Mock
    ObraRepository obraRepository;
    @Mock
    AutorMapper converter;
    @Mock
    AutorConverter autorConverter;
    @Mock
    ObraMapper obraMapper;
    @Mock
    AutorUpdateMapper updateMapper;

    AutorEntity autorEntity;
    AutorEntity autorEntityComCPF;
    AutorEntity autorEntityComCPFAtualizado;

    AutorRequestDTO autorRequestDTO;
    AutorRequestDTO autorRequestDTOComCPF;
    AutorRequestDTO autorRequestDTOComCPFAtualizado;

    AutorResponseDTO autorResponseDTO;
    AutorResponseDTO autorResponseDTOComCPF;
    AutorResponseDTO autorResponseDTOComCPFAtualizado;

    ObraResponseDTO obraResponseDTO;
    ObraEntity obraEntity;
    ObraEntity obraEntityComCPF;

    List<AutorResponseDTO> autorResponseDTOList;
    List<AutorEntity> autorEntityList;

    List<ObraResponseDTO> obraResponseDTOList;
    List<ObraRequestDTO> obraRequestDTOList;
    List<ObraEntity> obraEntityList;


    @BeforeEach
    void setup() throws ParseException {
        String birthDay = "03/07/1883";
        String published = "01/01/1915";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        autorEntity = new AutorEntity(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraEntityList);
        autorEntityComCPF = new AutorEntity(null, "Clarice Lispector", "Feminino", "clarice@lispector.com", formatter.parse(birthDay), "Brasil", "260.433.820-32", obraEntityList);
        autorEntityComCPFAtualizado = new AutorEntity(null, "Clarice Lispector da Silva", "Feminino", "clarice@lispector.com", formatter.parse(birthDay), "Brasil", "260.433.820-32", obraEntityList);
        obraEntity = new ObraEntity(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraEntityComCPF = new ObraEntity(null, "A Hora da Estrela", "O romance narra a história da datilógrafa alagoana, Macabéa, que migra para o Rio de Janeiro.", formatter.parse(published), null);

        autorResponseDTO = AutorResponseDTOFixture.build(null, "Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraResponseDTOList);
        autorResponseDTOComCPF = AutorResponseDTOFixture.build(null, "Clarice Lispector", "Feminino", "clarice@lispector.com", formatter.parse(birthDay), "Brasil", "260.433.820-32", obraResponseDTOList);
        autorResponseDTOComCPFAtualizado = AutorResponseDTOFixture.build(null, "Clarice Lispector da Silva", "Feminino", "clarice@lispector.com", formatter.parse(birthDay), "Brasil", "260.433.820-32", obraResponseDTOList);
        obraResponseDTO = ObraResponseDTOFixture.build(null, "Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published), null);
        obraResponseDTOList = List.of(obraResponseDTO);
        obraRequestDTOList = List.of(new ObraRequestDTO("Metamorfose", "A Metamorfose, de Franz Kafka, narra a história de Gregor Samsa, um caixeiro-viajante que, ao acordar, se encontra transformado em um inseto monstruoso.", formatter.parse(published)));

        autorRequestDTO = AutorRequestDTOFixture.build("Franz Kafka", "Masculino", "franz@kafka.com", formatter.parse(birthDay), "Tchéquia", "", obraRequestDTOList);
        autorRequestDTOComCPF = AutorRequestDTOFixture.build("Clarice Lispector", "Feminino", "clarice@lispector.com", formatter.parse(birthDay), "Brasil", "260.433.820-32", obraRequestDTOList);
        autorRequestDTOComCPFAtualizado = AutorRequestDTOFixture.build("Clarice Lispector da Silva", null, null, null, "", "260.433.820-32", null);

        autorResponseDTOList = List.of(autorResponseDTO);
        obraEntityList = List.of(obraEntity);
        autorEntityList = List.of(autorEntity);
    }

    @Test
    void deveCriarAutorComSucesso() {
        when(converter.forAuthorEntityFromAutorRequestDTO(autorRequestDTO)).thenReturn(autorEntity);
        when(repository.save(autorEntity)).thenReturn(autorEntity);
        when(converter.forAutorResponseDTOFromAutorEntity(autorEntity)).thenReturn(autorResponseDTO);
        AutorResponseDTO response = autorService.createAuthor(autorRequestDTO);
        assertEquals(autorResponseDTO, response);
    }

    @Test
    void deveCriarAutorComCPFComSucesso() {
        when(converter.forAuthorEntityFromAutorRequestDTO(autorRequestDTOComCPF)).thenReturn(autorEntityComCPF);
        when(repository.save(autorEntityComCPF)).thenReturn(autorEntityComCPF);
        when(converter.forAutorResponseDTOFromAutorEntity(autorEntityComCPF)).thenReturn(autorResponseDTOComCPF);
        AutorResponseDTO response = autorService.createAuthor(autorRequestDTOComCPF);
        assertEquals(autorResponseDTOComCPF, response);
    }

    // Campos obrigatórios: nome, e-mail e país
    @Test
    void deveFalharAoCriarAutorComAutorRequestDTOInvalido() {
        autorRequestDTOComCPF.setNome(" ");
        autorRequestDTOComCPF.setEmail("");
        autorRequestDTOComCPF.setPais("        ");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            autorService.createAuthor(autorRequestDTOComCPF);
        });
        assertEquals("Erro ao criar o autor: campos inválidos!", invalidFieldsException.getMessage());
    }

    @Test
    void deveFalharAoCriarAutorComCpfVazio() {
        autorRequestDTOComCPF.setCpf("");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.createAuthor(autorRequestDTOComCPF);
        });
        assertEquals("Para autores do brasil, o CPF é obrigatório!", cpfException.getMessage());
    }

    @Test
    void deveFalharAoCriarAutorComCpfInvalido() {
        autorRequestDTOComCPF.setCpf("260.433.820-37");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.createAuthor(autorRequestDTOComCPF);
        });
        assertEquals("Número de CPF inválido, tente novamente!", cpfException.getMessage());
    }

    @Test
    void deveBuscarAutorAtravesDoNomeComSucesso() {
        when(repository.findByNome("Clarice Lispector")).thenReturn(Optional.ofNullable(autorEntityComCPF));
        when(autorConverter.forAutorResponseDTOFromAutorEntityConverter(autorEntityComCPF)).thenReturn(autorResponseDTOComCPF);
        AutorResponseDTO response = autorService.findAuthorByName("Clarice Lispector");
        assertEquals(autorResponseDTOComCPF, response);
    }

    @Test
    void deveFalarAoBuscarAutorAtravesDoNome() {
        String nome = "Leandro";
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            autorService.findAuthorByName(nome);
        });
        assertEquals("Erro: o nome do autor " + nome + " não foi encontrado, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveListarTodosAutores() {
        when(repository.findAll()).thenReturn(autorEntityList);
        when(autorConverter.forAutorResponseDTOListFromAutorEntityListConverter(autorEntityList)).thenReturn(autorResponseDTOList);
        List<AutorResponseDTO> response = autorService.findAll();
        assertEquals(autorResponseDTOList, response);
    }

    @Test
    void deveRealizarUpdateAutorComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(autorEntityComCPF));
        when(updateMapper.updateAuthor(autorRequestDTOComCPFAtualizado, autorEntityComCPF)).thenReturn(autorEntityComCPFAtualizado);
        when(autorConverter.forAutorResponseDTOFromAutorEntityConverter(autorEntityComCPFAtualizado)).thenReturn(autorResponseDTOComCPFAtualizado);
        AutorResponseDTO response = autorService.updateAuthor(1L, autorRequestDTOComCPFAtualizado);
        assertEquals(autorResponseDTOComCPFAtualizado, response);
    }

    @Test
    void deveFalharAoFazerUpdateComCpfVazio() {
        autorRequestDTOComCPF.setCpf("");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.updateAuthor(1L, autorRequestDTOComCPF);
        });
        assertEquals("Para autores do brasil, o CPF é obrigatório!", cpfException.getMessage());
    }

    @Test
    void deveFalharAoFazerUpdateComCpfInvalido() {
        autorRequestDTOComCPF.setCpf("260.433.820-37");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.updateAuthor(1L, autorRequestDTOComCPF);
        });
        assertEquals("Número de CPF inválido, tente novamente!", cpfException.getMessage());
    }

    @Test
    void deveFalharAoFazerUpdateComIdInvalido() {
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            autorService.updateAuthor(1L, autorRequestDTOComCPF);
        });
        assertEquals("Erro ao atualizar o autor: id " + 1 + ", não encontrado, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveDeletarAutorComSucesso() {
        autorEntityComCPF.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(autorEntityComCPF));
        doNothing().when(repository).deleteById(1L);
        autorService.deleteAuthorById(1L);
        verify(repository, timeout(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveFalharAoDeletarAutorComIdInvalido() {
        Long invalidId = 32L;
        ResourceNotFoundException resourceNotFoundException = assertThrows(ResourceNotFoundException.class, () -> {
            autorService.deleteAuthorById(invalidId);
        });
        assertEquals("Erro ao deletar o autor: id " + invalidId + ", não encontrado, tente novamente!", resourceNotFoundException.getMessage());
    }

    @Test
    void deveRetornarErroSeOPaisDoAutorForBrasilECPFNaoPreenchido() {
        autorRequestDTOComCPF.setCpf("");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.verifyCpf(autorRequestDTOComCPF);
        });
        assertEquals("Para autores do brasil, o CPF é obrigatório!", cpfException.getMessage());
    }

    @Test
    void deveRetornarErroSeOPaisDoAutorForBrasilECPFInvalido() {
        autorRequestDTOComCPF.setCpf("260.433.820-34");
        CpfException cpfException = assertThrows(CpfException.class, () -> {
            autorService.verifyCpf(autorRequestDTOComCPF);
        });
        assertEquals("Número de CPF inválido, tente novamente!", cpfException.getMessage());
    }

    @Test
    void deveRetornarVerdadeiroSePaisAutorForBrasil() {
        boolean response = autorService.verifyCountry(autorRequestDTOComCPF.getPais());
        assertTrue(response);
    }

    @Test
    void deveRetornarFalseSePaisAutorNaoForBrasil() {
        boolean response = autorService.verifyCountry(autorRequestDTO.getPais());
        assertFalse(response);
    }

    @Test
    void deveRetornarErroSeNomeAutorForInvalido() {
        autorRequestDTOComCPF.setNome("");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            autorService.verifyFields(autorRequestDTOComCPF);
        });
        assertEquals("Erro ao criar o autor: campos inválidos!", invalidFieldsException.getMessage());
    }

    @Test
    void deveRetornarErroSeEmailAutorForInvalido() {
        autorRequestDTOComCPF.setEmail("");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            autorService.verifyFields(autorRequestDTOComCPF);
        });
        assertEquals("Erro ao criar o autor: campos inválidos!", invalidFieldsException.getMessage());
    }

    @Test
    void deveRetornarErroSePaisAutorForInvalido() {
        autorRequestDTOComCPF.setPais("");
        InvalidFieldsException invalidFieldsException = assertThrows(InvalidFieldsException.class, () -> {
            autorService.verifyFields(autorRequestDTOComCPF);
        });
        assertEquals("Erro ao criar o autor: campos inválidos!", invalidFieldsException.getMessage());
    }
}