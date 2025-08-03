package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.business.service.LoginService;
import com.math012.autoresobras.infra.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class UsuarioController {

    private final LoginService service;

    @PostMapping("/create/worker")
    @Operation(summary = "Endpoint para criar um usuário worker", description = "Criando um usuário com a autoridade worker")
    @ApiResponse(responseCode = "200", description = "Usuário worker criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar o usuário, nome de usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<LoginResponseDTO> createUserWorker(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.createUserWorker(loginRequestDTO));
    }


    @PostMapping("/create/admin")
    @Operation(summary = "Endpoint para criar um usuário admin", description = "Criando um usuário com a autoridade admin,worker")
    @ApiResponse(responseCode = "200", description = "Usuário admin/worker criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar o usuário, nome de usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<LoginResponseDTO> createUserAdmin(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.createUserAdmin(loginRequestDTO));
    }

    @PostMapping
    @Operation(summary = "Endpoint para autenticar usuário", description = "Autenticando e gerando token de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário admin/worker autenticado com sucesso")
    @ApiResponse(responseCode = "401", description = "Erro ao autenticar usuário, credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.login(loginRequestDTO));
    }
}
