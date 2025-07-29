package com.math012.autoresobras.business.converter;

import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.infra.entity.AutorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AutorConverter {

    private final ObraConverter obraConverter;

    public AutorResponseDTO forAutorResponseDTOFromAutorEntityConverter(AutorEntity entity) {
        return AutorResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .sexo(entity.getSexo())
                .email(entity.getEmail())
                .dataNascimento(entity.getDataNascimento())
                .pais(entity.getPais())
                .cpf(entity.getCpf())
                .obraEntityList(obraConverter.forObraResponseDTOListFromObraEntityListConverter(entity.getObraEntityList()))
                .build();
    }

    public List<AutorResponseDTO> forAutorResponseDTOListFromAutorEntityListConverter(List<AutorEntity> obraEntityList) {
        return obraEntityList.stream().map(this::forAutorResponseDTOFromAutorEntityConverter).toList();
    }
}