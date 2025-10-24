package com.example.autoescola.entity;

import com.example.autoescola.dto.EnderecoRecord;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    // Construtor para DTOs (Records)
    public Endereco(EnderecoRecord dto) {
        this.logradouro = dto.logradouro();
        this.numero = dto.numero();
        this.complemento = dto.complemento();
        this.bairro = dto.bairro();
        this.cidade = dto.cidade();
        this.uf = dto.uf();
        this.cep = dto.cep();
    }

    public void atualizar(EnderecoRecord dto) {
        if (dto.logradouro() != null) this.logradouro = dto.logradouro();
        if (dto.bairro() != null) this.bairro = dto.bairro();
        if (dto.cidade() != null) this.cidade = dto.cidade();
        if (dto.uf() != null) this.uf = dto.uf();
        if (dto.cep() != null) this.cep = dto.cep();

        // Numero e Complemento podem ser nulos
        this.numero = dto.numero();
        this.complemento = dto.complemento();
    }
}