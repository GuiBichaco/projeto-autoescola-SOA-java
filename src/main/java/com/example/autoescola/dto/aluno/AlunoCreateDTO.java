package com.example.autoescola.dto.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AlunoCreateDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String telefone,
        @NotBlank @CPF String cpf,
        @NotBlank String logradouro,
        String numero,
        String complemento,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String uf,
        @NotBlank String cep
) {}