package com.example.autoescola.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Atualização da Autorização
                .authorizeHttpRequests(req -> {
                    // Endpoints públicos
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers("/h2-console/**").permitAll();

                    // Endpoints de ADMIN (CRUD de Usuários)
                    // (Removendo "ROLE_")
                    req.requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("ADMIN");

                    // Endpoints de usuário logado (Qualquer role)
                    req.requestMatchers(HttpMethod.PUT, "/usuarios/minha-senha").authenticated();

                    // Todos os outros endpoints (alunos, instrutores, agendamentos)
                    // exigem autenticação, mas podem ser acessados por ROLE_USER ou ROLE_ADMIN
                    req.anyRequest().authenticated();
                })

                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}