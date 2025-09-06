package com.example.autoescola.repository;

import com.example.autoescola.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByInstrutorIdAndDataHoraAndCanceladoFalse(Long instrutorId, LocalDateTime dataHora);

    List<Agendamento> findByAlunoIdAndDataHoraBetweenAndCanceladoFalse(Long alunoId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}