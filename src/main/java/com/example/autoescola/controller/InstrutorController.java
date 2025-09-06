package com.example.autoescola.controller;

import com.example.autoescola.dto.instrutor.InstrutorCreateDTO;
import com.example.autoescola.dto.instrutor.InstrutorListDTO;
import com.example.autoescola.dto.instrutor.InstrutorUpdateDTO;
import com.example.autoescola.entity.Instrutor;
import com.example.autoescola.service.InstrutorService;
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
@RequestMapping("/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorService instrutorService;

    @PostMapping
    public ResponseEntity<Instrutor> create(@RequestBody @Valid InstrutorCreateDTO instrutorData, UriComponentsBuilder uriBuilder) {
        Instrutor instrutor = instrutorService.create(instrutorData);
        URI uri = uriBuilder.path("/instrutores/{id}").buildAndExpand(instrutor.getId()).toUri();
        return ResponseEntity.created(uri).body(instrutor);
    }

    @GetMapping
    public ResponseEntity<Page<InstrutorListDTO>> list(
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<InstrutorListDTO> page = instrutorService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> update(@PathVariable Long id, @RequestBody @Valid InstrutorUpdateDTO instrutorData) {
        Instrutor instrutor = instrutorService.update(id, instrutorData);
        return ResponseEntity.ok(instrutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instrutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}