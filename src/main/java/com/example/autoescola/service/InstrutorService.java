package com.example.autoescola.service;

import com.example.autoescola.dto.instrutor.InstrutorCreateDTO;
import com.example.autoescola.dto.instrutor.InstrutorListDTO;
import com.example.autoescola.dto.instrutor.InstrutorUpdateDTO;
import com.example.autoescola.entity.Instrutor;
import com.example.autoescola.repository.InstrutorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InstrutorService {

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Transactional
    public Instrutor create(InstrutorCreateDTO instrutorData) {
        Instrutor instrutor = new Instrutor();
        BeanUtils.copyProperties(instrutorData, instrutor);
        // O telefone não é preenchido no cadastro inicial
        instrutor.setTelefone("");
        return instrutorRepository.save(instrutor);
    }

    public Page<InstrutorListDTO> findAll(Pageable pageable) {
        return instrutorRepository.findAllByAtivoTrue(pageable)
                .map(instrutor -> new InstrutorListDTO(
                        instrutor.getId(),
                        instrutor.getNome(),
                        instrutor.getEmail(),
                        instrutor.getCnh(),
                        instrutor.getEspecialidade()
                ));
    }

    @Transactional
    public Instrutor update(Long id, InstrutorUpdateDTO instrutorData) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com o id: " + id));

            instrutor.setNome(instrutorData.nome());
            instrutor.setTelefone(instrutorData.telefone());
            instrutor.setLogradouro(instrutorData.logradouro());
            instrutor.setNumero(instrutorData.numero());
            instrutor.setComplemento(instrutorData.complemento());
            instrutor.setBairro(instrutorData.bairro());
            instrutor.setCidade(instrutorData.cidade());
            instrutor.setUf(instrutorData.uf());
            instrutor.setCep(instrutorData.cep());

        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public void delete(Long id) {
        Instrutor instrutor = instrutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrutor não encontrado com o id: " + id));
        instrutor.setAtivo(false);
        instrutorRepository.save(instrutor);
    }
}