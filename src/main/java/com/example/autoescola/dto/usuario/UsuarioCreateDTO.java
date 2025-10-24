package com.example.autoescola.dto.usuario;

import com.example.autoescola.enums.UsuarioRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO para um Admin criar um novo usuário
public record UsuarioCreateDTO(
        @NotBlank String login,
        @NotBlank String senha,
        @NotNull UsuarioRole role
) {}