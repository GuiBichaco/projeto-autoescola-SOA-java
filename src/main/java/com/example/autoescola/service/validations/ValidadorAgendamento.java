package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;

public interface ValidadorAgendamento {
    void validar(AgendamentoCreateDTO dados);
}