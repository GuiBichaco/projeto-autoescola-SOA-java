package com.example.autoescola.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRecord(
        @NotBlank
        String logradouro,
        String numero,
        String complemento,
        @NotBlank
        String bairro,
        @NotBlank
        String cidade,
        @NotBlank @Pattern(regexp = "\\S{2}") // Garante 2 caracteres
        String uf,
        @NotBlank @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}") // CEP 00000-000 ou 00000000
        String cep
) {}