package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.business.converter.mapper.ObraMapper;
import com.math012.autoresobras.business.converter.ObraConverter;
import com.math012.autoresobras.business.converter.update.ObraUpdateMapper;
import com.math012.autoresobras.infra.entity.ObraEntity;
import com.math012.autoresobras.infra.exceptions.api.InvalidFieldsException;
import com.math012.autoresobras.infra.exceptions.api.ResourceNotFoundException;
import com.math012.autoresobras.infra.repository.ObraRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ObraService {

    private final ObraRepository repository;
    private final ObraMapper converter;
    private final ObraConverter obraConverter;
    private final ObraUpdateMapper updateMapper;

    public Map<String, Object> findAllWithPages(int page, int size) {
        List<ObraEntity> obraEntityList;
        Pageable paging = PageRequest.of(page, size);
        Page<ObraEntity> pageWorks = repository.findAll(paging);
        obraEntityList = pageWorks.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("AuthorWorks", obraEntityList);
        response.put("currentPage", pageWorks.getNumber());
        response.put("totalItems", pageWorks.getTotalElements());
        response.put("totalPages", pageWorks.getTotalPages());
        return response;
    }

    public List<ObraResponseDTO> findAllAuthorsWorksByName(String nome) {
        List<ObraEntity> obraEntityList = repository.findObrasByAuthor(nome).orElseThrow(() -> new ResourceNotFoundException("Erro ao procurar o autor: nome " + nome + " inválido, tente novamente!"));
        return obraConverter.forObraResponseDTOListFromObraEntityListConverter(obraEntityList);
    }

    public ObraResponseDTO createAuthorsWorks(ObraRequestDTO request) {
        verifyFields(request);
        ObraEntity obraEntity = converter.forObraEntityFromObraRequestDTO(request);
        return converter.forObraResponseDTOFromObraEntity(repository.save(obraEntity));
    }

    public ObraResponseDTO updateAuthorsWorks(Long id, ObraRequestDTO obraRequestDTO) {
        ObraEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao atualizar a obra: id " + id + ", não encontrado, tente novamente!"));
        repository.save(updateMapper.updateAuthorsWorks(obraRequestDTO, entity));
        return obraConverter.forObraResponseDTOFromObraEntityConverter(repository.save(updateMapper.updateAuthorsWorks(obraRequestDTO, entity)));
    }

    public void deleteAuthorsWorksById(Long id) {
        ObraEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Erro ao deletar a obra: id " + id + ", não encontrado, tente novamente!"));
        repository.deleteById(entity.getId());
    }

    public void verifyFields(ObraRequestDTO request) {
        if (request.getNome().isBlank() || request.getDescricao().isBlank() || request.getDataPublicacao() == null) {
            throw new InvalidFieldsException("Erro ao criar a obra: campos inválidos!");
        }
    }
}
