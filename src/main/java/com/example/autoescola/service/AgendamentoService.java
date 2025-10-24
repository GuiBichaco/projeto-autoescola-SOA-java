package com.example.autoescola.service;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.dto.agendamento.CancelamentoDTO;
import com.example.autoescola.entity.Agendamento;
import com.example.autoescola.entity.Aluno;
import com.example.autoescola.entity.Instrutor;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.AgendamentoRepository;
import com.example.autoescola.repository.AlunoRepository;
import com.example.autoescola.repository.InstrutorRepository;
import com.example.autoescola.service.validations.ValidadorAgendamento;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private List<ValidadorAgendamento> validadores;

    @Transactional
    public Agendamento schedule(AgendamentoCreateDTO agendamentoData) {

        // 1. Executa todas as validações
        validadores.forEach(v -> v.validar(agendamentoData));

        // 2. Busca entidades
        Aluno aluno = alunoRepository.findById(agendamentoData.alunoId()).get(); // Já validado
        Instrutor instrutor = escolherInstrutor(agendamentoData);

        // 3. Salva o agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setDataHora(agendamentoData.dataHora());
        agendamento.setCancelado(false);

        return agendamentoRepository.save(agendamento);
    }

    private Instrutor escolherInstrutor(AgendamentoCreateDTO agendamentoData) {
        if (agendamentoData.instrutorId() != null) {
            // Se o ID foi passado, os validadores já checaram
            return instrutorRepository.findById(agendamentoData.instrutorId()).get();
        }

        // Se o ID não foi passado, escolhe um aleatório
        List<Instrutor> instrutoresDisponiveis = instrutorRepository.findAvailableInstructors(agendamentoData.dataHora());
        if (instrutoresDisponiveis.isEmpty()) {
            throw new ValidacaoException("Nenhum instrutor disponível para a data/hora selecionada.");
        }
        return instrutoresDisponiveis.get(new Random().nextInt(instrutoresDisponiveis.size()));
    }

    @Transactional
    public Agendamento cancel(Long id, CancelamentoDTO cancelamentoData) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));

        if (Duration.between(LocalDateTime.now(), agendamento.getDataHora()).toHours() < 24) {
            throw new ValidacaoException("Uma instrução somente poderá ser cancelada com antecedência mínima de 24 horas.");
        }

        if (agendamento.isCancelado()) {
            throw new IllegalStateException("Este agendamento já foi cancelado.");
        }

        agendamento.setCancelado(true);
        agendamento.setMotivoCancelamento(cancelamentoData.motivo());

        return agendamentoRepository.save(agendamento);
    }
}