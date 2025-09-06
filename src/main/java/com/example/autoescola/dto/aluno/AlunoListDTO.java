package com.example.autoescola.dto.aluno;

public record AlunoListDTO(
        Long id,
        String nome,
        String email,
        String cpf
) {}