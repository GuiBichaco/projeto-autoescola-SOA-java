package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public void validar(AgendamentoCreateDTO dados) {
        LocalDateTime dataHoraAgendamento = dados.dataHora();
        LocalDateTime startOfDay = dataHoraAgendamento.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = dataHoraAgendamento.toLocalDate().atTime(23, 59, 59);

        var agendamentosDoDia = agendamentoRepository.findByAlunoIdAndDataHoraBetweenAndCanceladoFalse(dados.alunoId(), startOfDay, endOfDay);
        if (agendamentosDoDia.size() >= 2) {
            throw new ValidacaoException("Um aluno não pode ter mais de duas instruções no mesmo dia.");
        }
    }
}