package com.example.autoescola.dto.agendamento;

import com.example.autoescola.enums.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record CancelamentoDTO(
        @NotNull MotivoCancelamento motivo
) {}