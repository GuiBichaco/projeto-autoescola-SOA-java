package com.example.autoescola.dto.instrutor;

import com.example.autoescola.dto.EnderecoRecord;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstrutorUpdateDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotNull @Valid EnderecoRecord endereco
) {}