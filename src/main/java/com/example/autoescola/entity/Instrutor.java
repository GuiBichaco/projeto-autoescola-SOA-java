package com.example.autoescola.entity;

import com.example.autoescola.dto.instrutor.InstrutorCreateDTO;
import com.example.autoescola.dto.instrutor.InstrutorUpdateDTO;
import com.example.autoescola.enums.Especialidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrutores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String telefone;

    @Column(unique = true)
    private String cnh;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private boolean ativo = true;

    // Construtor para o CreateDTO
    public Instrutor(InstrutorCreateDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.cnh = dto.cnh();
        this.especialidade = dto.especialidade();
        this.endereco = new Endereco(dto.endereco());
        this.telefone = ""; // Telefone oculto no cadastro
        this.ativo = true;
    }

    public void atualizar(InstrutorUpdateDTO dto) {
        if (dto.nome() != null) this.nome = dto.nome();
        if (dto.telefone() != null) this.telefone = dto.telefone();
        if (dto.endereco() != null) this.endereco.atualizar(dto.endereco());
    }
}