package com.example.autoescola.service;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.dto.agendamento.CancelamentoDTO;
import com.example.autoescola.entity.Agendamento;
import com.example.autoescola.entity.Aluno;
import com.example.autoescola.entity.Instrutor;
import com.example.autoescola.repository.AgendamentoRepository;
import com.example.autoescola.repository.AlunoRepository;
import com.example.autoescola.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
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

    @Transactional
    public Agendamento schedule(AgendamentoCreateDTO agendamentoData) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dataHoraAgendamento = agendamentoData.dataHora();

        // Validação de horário de funcionamento
        if (dataHoraAgendamento.getDayOfWeek() == DayOfWeek.SUNDAY ||
                dataHoraAgendamento.getHour() < 6 ||
                dataHoraAgendamento.getHour() >= 21) {
            throw new IllegalArgumentException("Agendamentos só podem ser feitos de Segunda a Sábado, das 06:00 às 21:00.");
        }

        // Validação de antecedência mínima
        if (Duration.between(now, dataHoraAgendamento).toMinutes() < 30) {
            throw new IllegalArgumentException("As instruções devem ser agendadas com no mínimo 30 minutos de antecedência.");
        }

        // Validação de aluno
        Aluno aluno = alunoRepository.findById(agendamentoData.alunoId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
        if (!aluno.isAtivo()) {
            throw new IllegalArgumentException("Não é permitido agendar com alunos inativos.");
        }

        // Validação de máximo de 2 instruções por dia para o mesmo aluno
        LocalDateTime startOfDay = dataHoraAgendamento.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = dataHoraAgendamento.toLocalDate().atTime(23, 59, 59);
        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByAlunoIdAndDataHoraBetweenAndCanceladoFalse(aluno.getId(), startOfDay, endOfDay);
        if (agendamentosDoDia.size() >= 2) {
            throw new IllegalArgumentException("Um aluno não pode ter mais de duas instruções no mesmo dia.");
        }

        Instrutor instrutor;
        // Se o instrutor foi especificado
        if (agendamentoData.instrutorId() != null) {
            instrutor = instrutorRepository.findById(agendamentoData.instrutorId())
                    .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado."));
            if (!instrutor.isAtivo()) {
                throw new IllegalArgumentException("Não é permitido agendar com instrutores inativos.");
            }
            // Valida se o instrutor já tem agendamento no mesmo horário
            if (agendamentoRepository.existsByInstrutorIdAndDataHoraAndCanceladoFalse(instrutor.getId(), dataHoraAgendamento)) {
                throw new IllegalArgumentException("O instrutor selecionado já possui uma instrução agendada para esta data/hora.");
            }
        } else { // Se o instrutor não foi especificado, escolhe um aleatório
            List<Instrutor> instrutoresDisponiveis = instrutorRepository.findAvailableInstructors(dataHoraAgendamento);
            if (instrutoresDisponiveis.isEmpty()) {
                throw new IllegalStateException("Nenhum instrutor disponível para a data/hora selecionada.");
            }
            instrutor = instrutoresDisponiveis.get(new Random().nextInt(instrutoresDisponiveis.size()));
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setDataHora(dataHoraAgendamento);
        agendamento.setCancelado(false);

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento cancel(Long id, CancelamentoDTO cancelamentoData) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));

        // Validação de antecedência mínima para cancelamento
        if (Duration.between(LocalDateTime.now(), agendamento.getDataHora()).toHours() < 24) {
            throw new IllegalArgumentException("Uma instrução somente poderá ser cancelada com antecedência mínima de 24 horas.");
        }

        if (agendamento.isCancelado()) {
            throw new IllegalStateException("Este agendamento já foi cancelado.");
        }

        agendamento.setCancelado(true);
        agendamento.setMotivoCancelamento(cancelamentoData.motivo());

        return agendamentoRepository.save(agendamento);
    }
}