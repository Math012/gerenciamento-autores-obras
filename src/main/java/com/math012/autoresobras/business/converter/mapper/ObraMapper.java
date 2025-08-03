package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.business.DTO.response.ObraResponseDTO;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ObraMapper {
    ObraEntity forObraEntityFromObraRequestDTO(ObraRequestDTO dto);

    ObraResponseDTO forObraResponseDTOFromObraEntity(ObraEntity entity);

    List<ObraResponseDTO> forObraResponseDTOListFromObraEntityList(List<ObraEntity> obraEntityList);

}
