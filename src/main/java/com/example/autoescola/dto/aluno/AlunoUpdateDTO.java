package com.example.autoescola.dto.aluno;

import jakarta.validation.constraints.NotBlank;

public record AlunoUpdateDTO(
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