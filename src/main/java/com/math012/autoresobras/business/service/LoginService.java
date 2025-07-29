package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.business.converter.mapper.LoginMapper;
import com.math012.autoresobras.infra.entity.UsuarioEntity;
import com.math012.autoresobras.infra.enums.Roles;
import com.math012.autoresobras.infra.exceptions.api.ConflictException;
import com.math012.autoresobras.infra.repository.UsuarioRepository;
import com.math012.autoresobras.infra.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LoginService {

    private final LoginMapper mapper;
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO createUserWorker(LoginRequestDTO loginRequestDTO) {
        verifyUsername(loginRequestDTO.getUsername());
        List<String> role = List.of(Roles.WORKER.name());
        loginRequestDTO.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
        UsuarioEntity entity = mapper.forUsuarioEntityFromLoginRequestDTO(loginRequestDTO);
        entity.setRoles(role);
        return mapper.forLoginResponseDTOFromUsuarioEntity(repository.save(entity));
    }

    public LoginResponseDTO createUserAdmin(LoginRequestDTO loginRequestDTO) {
        verifyUsername(loginRequestDTO.getUsername());
        List<String> role = List.of(Roles.WORKER.name(), Roles.ADMIN.name());
        loginRequestDTO.setPassword(passwordEncoder.encode(loginRequestDTO.getPassword()));
        UsuarioEntity entity = mapper.forUsuarioEntityFromLoginRequestDTO(loginRequestDTO);
        entity.setRoles(role);
        return mapper.forLoginResponseDTOFromUsuarioEntity(repository.save(entity));
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public void verifyUsername(String username) {
        if (usernameExists(username)) {
            throw new ConflictException("Erro ao cadastrar usuário: nome de usuário " + username + " já está cadastrado, tente novamente!");
        }
    }

    public boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }
}