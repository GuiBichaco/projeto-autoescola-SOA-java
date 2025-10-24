package com.example.autoescola.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacaoDTO(
        @NotBlank String login,
        @NotBlank String senha
) {}