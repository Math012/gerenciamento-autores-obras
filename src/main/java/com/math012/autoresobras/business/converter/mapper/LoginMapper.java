package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.infra.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    UsuarioEntity forUsuarioEntityFromLoginRequestDTO(LoginRequestDTO loginRequestDTO);
    LoginResponseDTO forLoginResponseDTOFromUsuarioEntity(UsuarioEntity usuarioEntity);
}
