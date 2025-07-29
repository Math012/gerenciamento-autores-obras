package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
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
import com.math012.autoresobras.infra.utils.CPFValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@AllArgsConstructor
@Service
public class AutorService {

    private final AutorRepository repository;
    private final ObraRepository obraRepository;
    private final AutorMapper converter;
    private final AutorConverter autorConverter;
    private final ObraMapper obraMapper;
    private final AutorUpdateMapper updateMapper;

    public AutorResponseDTO createAuthor(AutorRequestDTO request) {
        verifyFields(request);
        verifyCpf(request);
        List<ObraEntity> obraEntityList = getObras(request.getObraEntityList());
        AutorEntity autorEntity = converter.forAuthorEntityFromAutorRequestDTO(request);
        autorEntity.setObraEntityList(obraEntityList);
        return converter.forAutorResponseDTOFromAutorEntity(repository.save(autorEntity));
    }

    public AutorResponseDTO findAuthorByName(String nome) {
        AutorEntity autorEntity = repository.findByNome(nome).orElseThrow(() -> new ResourceNotFoundException("Erro: o nome do autor " + nome + " não foi encontrado, tente novamente!"));
        return autorConverter.forAutorResponseDTOFromAutorEntityConverter(autorEntity);
    }

    public Map<String, Object> findAllAuthorsWithPage(int page, int size) {
        List<AutorEntity> autorEntityList;
        Pageable paging = PageRequest.of(page, size);
        Page<AutorEntity> pageAuthor = repository.findAll(paging);
        autorEntityList = pageAuthor.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("Authors", autorEntityList);
        response.put("currentPage", pageAuthor.getNumber());
        response.put("totalItems", pageAuthor.getTotalElements());
        response.put("totalPages", pageAuthor.getTotalPages());
        return response;
    }

    public List<AutorResponseDTO> findAll() {
        return autorConverter.forAutorResponseDTOListFromAutorEntityListConverter(repository.findAll());
    }

    public AutorResponseDTO updateAuthor(Long id, AutorRequestDTO request) {
        verifyCpf(request);
        AutorEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao atualizar o autor: id " + id + ", não encontrado, tente novamente!"));
        return autorConverter.forAutorResponseDTOFromAutorEntityConverter(updateMapper.updateAuthor(request, entity));
    }

    public void deleteAuthorById(Long id) {
        AutorEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar o autor: id " + id + ", não encontrado, tente novamente!"));
        repository.deleteById(entity.getId());
    }

    public List<ObraEntity> getObras(List<ObraRequestDTO> obraRequestDTOList) {
        return obraRequestDTOList.stream().map(dto -> {
            ObraEntity obraEntity = obraRepository.findByNome(dto.getNome()).orElseGet(() -> {
                ObraEntity newObra = obraMapper.forObraEntityFromObraRequestDTO(dto);
                return obraRepository.save(newObra);
            });

            return obraEntity;
        }).toList();
    }

    public void verifyCpf(AutorRequestDTO request) {
        if (verifyCountry(request.getPais()) && request.getCpf().isBlank()) {
            throw new CpfException("Para autores do brasil, o CPF é obrigatório!");
        } else {
            if (verifyCountry(request.getPais()) && request.getCpf() != null) {
                if (!CPFValidator.isCPF(request.getCpf())) {
                    throw new CpfException("Número de CPF inválido, tente novamente!");
                }
            }
        }
    }

    public boolean verifyCountry(String country) {
        String countryFormated = country.toLowerCase().replace(" ", "");
        if (countryFormated.equals("brasil")) {
            return true;
        }
        return false;
    }

    public void verifyFields(AutorRequestDTO request) {
        if (request.getNome().isBlank() || request.getEmail().isBlank() || request.getPais().isBlank()) {
            throw new InvalidFieldsException("Erro ao criar o autor: campos inválidos!");
        }
    }
}