package com.math012.autoresobras.infra.security;

import com.math012.autoresobras.infra.exceptions.security.CustomAccessDeniedHandler;
import com.math012.autoresobras.infra.exceptions.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Swagger implements
    public static final String SECURITY_SCHEME = "bearerAuth";

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil, userDetailsService);
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // LOGIN ENDPOINT
                        .requestMatchers("/login/**").permitAll()
                        // AUTHOR ENDPOINTS, METHODS AND ROLES
                        .requestMatchers(HttpMethod.POST, "/author/create").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/author/find/name").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/author/find/all/page").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/author/find/all").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/author/update/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/author/delete/**").hasAuthority("ADMIN")
                        // AUTHORS WORK ENDPOINTS, METHODS AND ROLES
                        .requestMatchers(HttpMethod.POST, "/author/works/create").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/author/works/page/all").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/author/works/name").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/author/works/update/**").hasAnyAuthority("WORKER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/author/works/delete/**").hasAnyAuthority("WORKER", "ADMIN")

                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}