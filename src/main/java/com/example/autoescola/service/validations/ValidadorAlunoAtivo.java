package com.example.autoescola.service.validations;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAlunoAtivo implements ValidadorAgendamento {
    @Autowired
    private AlunoRepository alunoRepository;

    public void validar(AgendamentoCreateDTO dados) {
        var aluno = alunoRepository.findById(dados.alunoId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
        if (!aluno.isAtivo()) {
            throw new ValidacaoException("Não é permitido agendar com alunos inativos.");
        }
    }
}