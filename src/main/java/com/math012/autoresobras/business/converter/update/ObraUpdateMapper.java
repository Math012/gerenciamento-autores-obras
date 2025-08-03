package com.math012.autoresobras.business.converter.update;

import com.math012.autoresobras.business.DTO.request.ObraRequestDTO;
import com.math012.autoresobras.infra.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface ObraUpdateMapper {
    ObraEntity updateAuthorsWorks(ObraRequestDTO dto, @MappingTarget ObraEntity entity);
}
