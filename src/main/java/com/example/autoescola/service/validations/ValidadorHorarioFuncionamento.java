package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {
    public void validar(AgendamentoCreateDTO dados) {
        LocalDateTime dataHoraAgendamento = dados.dataHora();
        if (dataHoraAgendamento.getDayOfWeek() == DayOfWeek.SUNDAY ||
                dataHoraAgendamento.getHour() < 6 ||
                dataHoraAgendamento.getHour() >= 21) {
            throw new ValidacaoException("Agendamentos só podem ser feitos de Segunda a Sábado, das 06:00 às 21:00.");
        }
    }
}