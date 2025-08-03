package com.math012.autoresobras.business.converter.update;

import com.math012.autoresobras.business.DTO.request.AutorRequestDTO;
import com.math012.autoresobras.infra.entity.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AutorUpdateMapper {
    AutorEntity updateAuthor(AutorRequestDTO dto, @MappingTarget AutorEntity entity);
}
