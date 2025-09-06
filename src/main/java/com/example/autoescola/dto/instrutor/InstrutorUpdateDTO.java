package com.example.autoescola.dto.instrutor;

import jakarta.validation.constraints.NotBlank;

public record InstrutorUpdateDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotBlank String logradouro,
        String numero,
        String complemento,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String uf,
        @NotBlank String cep
) {}