package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.business.DTO.response.AutorResponseDTO;
import com.math012.autoresobras.infra.entity.AutorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorEntity forAuthorEntityFromAutorRequestDTO(AutorRequestDTO dto);

    AutorResponseDTO forAutorResponseDTOFromAutorEntity(AutorEntity entity);

    List<AutorResponseDTO> forAutorResponseDTOListFrom(List<AutorEntity> entity);

    List<AutorEntity> forAutorEntityListFromAutorResponseDTOList(List<AutorResponseDTO> dtoList);

}
