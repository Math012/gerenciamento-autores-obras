package com.math012.autoresobras.business.converter.mapper;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.request.LoginRequestDTOFixture;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTOFixture;
import com.math012.autoresobras.infra.entity.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginMapperTest {

    LoginMapper loginMapper;
    UsuarioEntity usuarioEntity;
    LoginRequestDTO loginRequestDTO;
    LoginResponseDTO loginResponseDTO;
    List<String> roles = new ArrayList<>();

    @BeforeEach
    void setup() {
        loginMapper = Mappers.getMapper(LoginMapper.class);
        roles.add("ADMIN");
        usuarioEntity = new UsuarioEntity(null, "admin", "1234", roles);
        loginRequestDTO = LoginRequestDTOFixture.build("admin", "1234");
        loginResponseDTO = LoginResponseDTOFixture.build(null, "admin", "1234", roles);
    }

    @Test
    void deveConverterDeLoginRequestDTOParaUsuarioEntity() {
        UsuarioEntity response = loginMapper.forUsuarioEntityFromLoginRequestDTO(loginRequestDTO);
        response.setRoles(roles);
        assertEquals(usuarioEntity, response);
    }

    @Test
    void deveConverterDeUsuarioEntityParaLoginResponseDTO() {
        LoginResponseDTO response = loginMapper.forLoginResponseDTOFromUsuarioEntity(usuarioEntity);
        assertEquals(loginResponseDTO, response);
    }
}