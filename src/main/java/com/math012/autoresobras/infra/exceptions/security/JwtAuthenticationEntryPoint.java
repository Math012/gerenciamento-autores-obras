package com.math012.autoresobras.infra.exceptions.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String error;

        if (authException.getCause() instanceof ExpiredJwtException) {
            error = "Token expirado, faça login novamente!";
        } else if (authException.getCause() instanceof MalformedJwtException) {
            error = "Token inválido!";
        } else {
            error = "Falha na autenticação";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                "{ \"error\": \"" + error + "\" }"
        );

    }
}
