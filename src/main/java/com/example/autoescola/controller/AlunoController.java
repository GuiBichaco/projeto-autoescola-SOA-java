package com.example.autoescola.controller;

import com.example.autoescola.dto.aluno.AlunoCreateDTO;
import com.example.autoescola.dto.aluno.AlunoListDTO;
import com.example.autoescola.dto.aluno.AlunoUpdateDTO;
import com.example.autoescola.entity.Aluno;
import com.example.autoescola.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Aluno> create(@RequestBody @Valid AlunoCreateDTO alunoData, UriComponentsBuilder uriBuilder) {
        Aluno aluno = alunoService.create(alunoData);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(aluno);
    }

    @GetMapping
    public ResponseEntity<Page<AlunoListDTO>> list(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<AlunoListDTO> page = alunoService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody @Valid AlunoUpdateDTO alunoData) {
        Aluno aluno = alunoService.update(id, alunoData);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}