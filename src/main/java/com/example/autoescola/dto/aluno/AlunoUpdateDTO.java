package com.example.autoescola.dto.aluno;

import com.example.autoescola.dto.EnderecoRecord;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlunoUpdateDTO(
        @NotBlank String nome,
        @NotBlank String telefone,
        @NotNull @Valid EnderecoRecord endereco
) {}