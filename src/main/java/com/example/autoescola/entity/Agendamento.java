package com.example.autoescola.entity;

import com.example.autoescola.enums.MotivoCancelamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    private LocalDateTime dataHora;

    private boolean cancelado = false;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;
}