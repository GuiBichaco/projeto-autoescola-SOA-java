package com.example.autoescola.dto.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoCreateDTO(
        @NotNull Long alunoId,
        Long instrutorId, // Opcional
        @NotNull @Future LocalDateTime dataHora
) {}