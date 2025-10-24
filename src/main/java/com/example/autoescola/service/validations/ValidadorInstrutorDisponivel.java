package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInstrutorDisponivel implements ValidadorAgendamento {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public void validar(AgendamentoCreateDTO dados) {
        // Esta validação só se aplica se um instrutor foi escolhido
        if (dados.instrutorId() == null) {
            return;
        }

        if (agendamentoRepository.existsByInstrutorIdAndDataHoraAndCanceladoFalse(dados.instrutorId(), dados.dataHora())) {
            throw new ValidacaoException("O instrutor selecionado já possui uma instrução agendada para esta data/hora.");
        }
    }
}