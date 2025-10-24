package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamento {
    public void validar(AgendamentoCreateDTO dados) {
        if (Duration.between(LocalDateTime.now(), dados.dataHora()).toMinutes() < 30) {
            throw new ValidacaoException("As instruções devem ser agendadas com no mínimo 30 minutos de antecedência.");
        }
    }
}