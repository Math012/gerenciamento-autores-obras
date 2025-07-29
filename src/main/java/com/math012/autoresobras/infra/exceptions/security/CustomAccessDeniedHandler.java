package com.math012.autoresobras.infra.exceptions.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


// Retorno de exceções relacionadas ao Spring Security
// AccessDeniedHandler para usuários com divergência de autoridade.

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI =  request.getRequestURI();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userAuthorities = auth.getAuthorities().toString();

        String error = String.format("Acesso negado!, Você não tem permissão para acessar a rota '%s' com suas autoridades: '%s' ",
                requestURI,
                userAuthorities);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{ \"error\": \"" + error + "\" }");
    }
}
