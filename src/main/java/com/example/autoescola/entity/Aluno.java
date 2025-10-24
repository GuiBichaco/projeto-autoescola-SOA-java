package com.example.autoescola.entity;

import com.example.autoescola.dto.aluno.AlunoCreateDTO;
import com.example.autoescola.dto.aluno.AlunoUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alunos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String telefone;

    @Column(unique = true)
    private String cpf;

    @Embedded
    private Endereco endereco;

    private boolean ativo = true;

    // Construtor para o CreateDTO
    public Aluno(AlunoCreateDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.cpf = dto.cpf();
        this.endereco = new Endereco(dto.endereco());
        this.ativo = true;
    }

    public void atualizar(AlunoUpdateDTO dto) {
        if (dto.nome() != null) this.nome = dto.nome();
        if (dto.telefone() != null) this.telefone = dto.telefone();
        if (dto.endereco() != null) this.endereco.atualizar(dto.endereco());
    }
}