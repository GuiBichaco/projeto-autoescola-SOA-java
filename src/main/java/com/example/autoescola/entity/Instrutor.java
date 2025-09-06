package com.example.autoescola.entity;

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

    // Endereço
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    private boolean ativo = true;
}