package com.example.autoescola.dto.instrutor;

import com.example.autoescola.enums.Especialidade;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstrutorCreateDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String cnh,
        @NotNull Especialidade especialidade,
        @NotBlank String logradouro,
        String numero,
        String complemento,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String uf,
        @NotBlank String cep
) {}