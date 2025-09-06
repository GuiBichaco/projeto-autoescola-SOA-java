package com.example.autoescola.dto.instrutor;

import com.example.autoescola.enums.Especialidade;

public record InstrutorListDTO(
        Long id,
        String nome,
        String email,
        String cnh,
        Especialidade especialidade
) {}