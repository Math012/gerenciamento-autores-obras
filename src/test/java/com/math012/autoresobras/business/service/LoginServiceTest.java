package com.math012.autoresobras.business.service;

import com.math012.autoresobras.business.DTO.request.LoginRequestDTO;
import com.math012.autoresobras.business.DTO.request.LoginRequestDTOFixture;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTO;
import com.math012.autoresobras.business.DTO.response.LoginResponseDTOFixture;
import com.math012.autoresobras.business.converter.mapper.LoginMapper;
import com.math012.autoresobras.infra.entity.UsuarioEntity;
import com.math012.autoresobras.infra.exceptions.api.ConflictException;
import com.math012.autoresobras.infra.repository.UsuarioRepository;
import com.math012.autoresobras.infra.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    private LoginMapper mapper;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    UsuarioEntity usuarioEntityAdmin;
    UsuarioEntity usuarioEntityWorker;

    LoginRequestDTO loginRequestDTOAdmin;
    LoginResponseDTO loginResponseDTOAdmin;

    LoginRequestDTO loginRequestDTOWorker;
    LoginResponseDTO loginResponseDTOWorker;

    List<String> rolesAdminAndWorker = new ArrayList<>();
    List<String> rolesWorker = new ArrayList<>();

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    Authentication authentication;
    String token;

    @BeforeEach
    void setup() {
        rolesWorker.add("WORKER");
        rolesAdminAndWorker.add("WORKER, ADMIN");

        usuarioEntityAdmin = new UsuarioEntity(1L, "admin", "1234", null);
        usuarioEntityWorker = new UsuarioEntity(1L, "admin", "1234", null);

        loginRequestDTOAdmin = LoginRequestDTOFixture.build("admin", "1234");
        loginResponseDTOAdmin = LoginResponseDTOFixture.build(1L, "admin", "1234", rolesAdminAndWorker);

        loginRequestDTOWorker = LoginRequestDTOFixture.build("worker", "1234");
        loginResponseDTOWorker = LoginResponseDTOFixture.build(1L, "worker", "1234", rolesWorker);

        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTOAdmin.getUsername(), loginRequestDTOAdmin.getPassword());
        authentication = usernamePasswordAuthenticationToken;
        token = "ft854xDd5wU7WC27Z7ZRRMHPzGP3ILXvV5Bsh1AZ5xE4bXaX9xRYD9a722AaHezT";
    }

    @Test
    void deveCriarUsuarioComAutoridadeAdminEWorker() {
        when(passwordEncoder.encode(loginRequestDTOAdmin.getPassword())).thenReturn("1234");
        when(mapper.forUsuarioEntityFromLoginRequestDTO(loginRequestDTOAdmin)).thenReturn(usuarioEntityAdmin);
        when(repository.save(usuarioEntityAdmin)).thenReturn(usuarioEntityAdmin);
        when(mapper.forLoginResponseDTOFromUsuarioEntity(usuarioEntityAdmin)).thenReturn(loginResponseDTOAdmin);
        LoginResponseDTO response = loginService.createUserAdmin(loginRequestDTOAdmin);
        assertEquals(loginResponseDTOAdmin, response);
    }

    @Test
    void deveFalharAoCriarUsuarioAdminEWorkerComNomeDeUsuarioJaRegistrado() {
        String username = "admin";
        loginRequestDTOAdmin.setUsername(username);
        when(repository.existsByUsername(username)).thenReturn(true);
        ConflictException conflictException = assertThrows(ConflictException.class, () -> {
            loginService.createUserAdmin(loginRequestDTOAdmin);
        });
        assertEquals("Erro ao cadastrar usuário: nome de usuário " + username + " já está cadastrado, tente novamente!", conflictException.getMessage());
    }

    @Test
    void deveCriarUsuarioComAutoridadeWorker() {
        when(passwordEncoder.encode(loginRequestDTOWorker.getPassword())).thenReturn("1234");
        when(mapper.forUsuarioEntityFromLoginRequestDTO(loginRequestDTOWorker)).thenReturn(usuarioEntityWorker);
        when(repository.save(usuarioEntityWorker)).thenReturn(usuarioEntityWorker);
        when(mapper.forLoginResponseDTOFromUsuarioEntity(usuarioEntityWorker)).thenReturn(loginResponseDTOWorker);
        LoginResponseDTO response = loginService.createUserWorker(loginRequestDTOWorker);
        assertEquals(loginResponseDTOWorker, response);
    }

    @Test
    void deveFalharAoCriarUsuarioWorkerComNomeDeUsuarioJaRegistrado() {
        String username = "worker";
        loginRequestDTOAdmin.setUsername(username);
        when(repository.existsByUsername(username)).thenReturn(true);
        ConflictException conflictException = assertThrows(ConflictException.class, () -> {
            loginService.createUserAdmin(loginRequestDTOAdmin);
        });
        assertEquals("Erro ao cadastrar usuário: nome de usuário " + username + " já está cadastrado, tente novamente!", conflictException.getMessage());
    }

    @Test
    void deveRealizarLoginComSucesso() {
        when(authenticationManager.authenticate(usernamePasswordAuthenticationToken)).thenReturn(authentication);
        when(jwtUtil.generateToken(authentication.getName())).thenReturn(token);
        String response = loginService.login(loginRequestDTOAdmin);
        assertEquals("Bearer " + token, response);
    }

    @Test
    void deveRetornarErroSeUsernameJaCadastrado() {
        String username = "admin";
        when(repository.existsByUsername(username)).thenReturn(true);
        ConflictException conflictException = assertThrows(ConflictException.class, () -> {
            loginService.verifyUsername(username);
        });
        assertEquals("Erro ao cadastrar usuário: nome de usuário " + username + " já está cadastrado, tente novamente!", conflictException.getMessage());
    }

    @Test
    void deveRetornarVerdadeiroSeUsernameJaCadastrado() {
        String username = "admin";
        when(repository.existsByUsername(username)).thenReturn(true);
        boolean response = loginService.usernameExists(username);
        assertTrue(response);
    }

    @Test
    void deveRetornarFalseSeUsernameNaoCadastrado() {
        String username = "admin";
        when(repository.existsByUsername(username)).thenReturn(false);
        boolean response = loginService.usernameExists(username);
        assertFalse(response);
    }
}