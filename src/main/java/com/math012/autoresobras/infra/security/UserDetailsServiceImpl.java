package com.math012.autoresobras.infra.security;

import com.math012.autoresobras.infra.entity.UsuarioEntity;
import com.math012.autoresobras.infra.exceptions.api.ResourceNotFoundException;
import com.math012.autoresobras.infra.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity entity = repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Erro ao localizar usuario: usuario não encontrado!"));
        return User.withUsername(entity.getUsername())
                .password(entity.getPassword())
                .authorities(entity.getAuthorities())
                .build();
    }
}