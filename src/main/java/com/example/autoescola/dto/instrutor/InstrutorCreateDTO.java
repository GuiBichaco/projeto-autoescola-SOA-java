package com.example.autoescola.dto.instrutor;

import com.example.autoescola.dto.EnderecoRecord;
import com.example.autoescola.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstrutorCreateDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String cnh,
        @NotNull Especialidade especialidade,
        @NotNull @Valid EnderecoRecord endereco
) {}