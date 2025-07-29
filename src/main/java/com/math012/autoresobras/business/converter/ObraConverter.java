package com.math012.autoresobras.business.converter;

import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObraConverter {

    public ObraResponseDTO forObraResponseDTOFromObraEntityConverter(ObraEntity obraEntity) {
        return ObraResponseDTO.builder()
                .id(obraEntity.getId())
                .nome(obraEntity.getNome())
                .descricao(obraEntity.getDescricao())
                .dataPublicacao(obraEntity.getDataPublicacao())
                .autorEntityList(null)
                .build();
    }

    public List<ObraResponseDTO> forObraResponseDTOListFromObraEntityListConverter(List<ObraEntity> obraEntityList) {
        return obraEntityList.stream().map(this::forObraResponseDTOFromObraEntityConverter).toList();
    }
}