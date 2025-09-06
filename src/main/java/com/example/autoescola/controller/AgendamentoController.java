package com.example.autoescola.controller;

import com.example.autoescola.dto.agendamento.AgendamentoCreateDTO;
import com.example.autoescola.dto.agendamento.CancelamentoDTO;
import com.example.autoescola.entity.Agendamento;
import com.example.autoescola.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> schedule(@RequestBody @Valid AgendamentoCreateDTO agendamentoData, UriComponentsBuilder uriBuilder) {
        Agendamento agendamento = agendamentoService.schedule(agendamentoData);
        URI uri = uriBuilder.path("/agendamentos/{id}").buildAndExpand(agendamento.getId()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Agendamento> cancel(@PathVariable Long id, @RequestBody @Valid CancelamentoDTO cancelamentoData) {
        Agendamento agendamento = agendamentoService.cancel(id, cancelamentoData);
        return ResponseEntity.ok(agendamento);
    }
}