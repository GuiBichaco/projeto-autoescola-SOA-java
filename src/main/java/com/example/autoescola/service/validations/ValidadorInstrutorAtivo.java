package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInstrutorAtivo implements ValidadorAgendamento {
    @Autowired
    private InstrutorRepository instrutorRepository;

    public void validar(AgendamentoCreateDTO dados) {
        // Esta validação só se aplica se um instrutor foi escolhido
        if (dados.instrutorId() == null) {
            return;
        }

        var instrutor = instrutorRepository.findById(dados.instrutorId())
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado."));
        if (!instrutor.isAtivo()) {
            throw new ValidacaoException("Não é permitido agendar com instrutores inativos.");
        }
    }
}