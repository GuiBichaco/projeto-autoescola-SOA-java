package com.example.autoescola.dto.usuario;

import com.example.autoescola.enums.UsuarioRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO para um Admin atualizar um usuário
public record UsuarioUpdateDTO(
        @NotBlank String login,
        @NotNull UsuarioRole role
) {}