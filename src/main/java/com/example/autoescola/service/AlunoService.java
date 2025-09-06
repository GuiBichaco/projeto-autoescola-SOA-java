package com.example.autoescola.service;

import com.example.autoescola.dto.aluno.AlunoCreateDTO;
import com.example.autoescola.dto.aluno.AlunoListDTO;
import com.example.autoescola.dto.aluno.AlunoUpdateDTO;
import com.example.autoescola.entity.Aluno;
import com.example.autoescola.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public Aluno create(AlunoCreateDTO alunoData) {
        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoData, aluno);
        return alunoRepository.save(aluno);
    }

    public Page<AlunoListDTO> findAll(Pageable pageable) {
        return alunoRepository.findAllByAtivoTrue(pageable)
                .map(aluno -> new AlunoListDTO(
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getEmail(),
                        aluno.getCpf()
                ));
    }

    @Transactional
    public Aluno update(Long id, AlunoUpdateDTO alunoData) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com o id: " + id));

            aluno.setNome(alunoData.nome());
            aluno.setTelefone(alunoData.telefone());
            aluno.setLogradouro(alunoData.logradouro());
            aluno.setNumero(alunoData.numero());
            aluno.setComplemento(alunoData.complemento());
            aluno.setBairro(alunoData.bairro());
            aluno.setCidade(alunoData.cidade());
            aluno.setUf(alunoData.uf());
            aluno.setCep(alunoData.cep());

        return alunoRepository.save(aluno);
    }

    @Transactional
    public void delete(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado com o id: " + id));
        aluno.setAtivo(false);
        alunoRepository.save(aluno);
    }
}