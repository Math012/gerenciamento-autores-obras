package com.math012.autoresobras.controller;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.business.service.LoginService;
import com.math012.autoresobras.infra.security.JwtUtil;
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
    public ResponseEntity<LoginResponseDTO> createUserWorker(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.createUserWorker(loginRequestDTO));
    }


    @PostMapping("/create/admin")
    public ResponseEntity<LoginResponseDTO> createUserAdmin(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.createUserAdmin(loginRequestDTO));
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(service.login(loginRequestDTO));
    }
}
