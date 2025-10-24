package com.example.autoescola.dto.usuario;

import jakarta.validation.constraints.NotBlank;

// DTO para um usuário alterar a própria senha
public record UsuarioChangePasswordDTO(
        @NotBlank String senhaAtual,
        @NotBlank String novaSenha
) {}