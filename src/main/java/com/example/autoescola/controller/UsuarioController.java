package com.example.autoescola.controller;

import com.example.autoescola.dto.usuario.UsuarioChangePasswordDTO;
import com.example.autoescola.dto.usuario.UsuarioCreateDTO;
import com.example.autoescola.dto.usuario.UsuarioListDTO;
import com.example.autoescola.dto.usuario.UsuarioUpdateDTO;
import com.example.autoescola.entity.Usuario;
import com.example.autoescola.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint 1: (Admin) Criar novo usuário
    @PostMapping
    public ResponseEntity<UsuarioListDTO> create(@RequestBody @Valid UsuarioCreateDTO dto, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.create(dto);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioListDTO(usuario));
    }

    // Endpoint 2: (Admin) Listar todos os usuários
    @GetMapping
    public ResponseEntity<Page<UsuarioListDTO>> list(@PageableDefault(size = 10, sort = "login") Pageable pageable) {
        Page<UsuarioListDTO> page = usuarioService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    // Endpoint 3: (Admin) Atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListDTO> update(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        UsuarioListDTO updatedUser = usuarioService.update(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint 4: (Admin) Excluir um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 5: (Qualquer Usuário) Alterar a própria senha
    @PutMapping("/minha-senha")
    public ResponseEntity<String> changePassword(@RequestBody @Valid UsuarioChangePasswordDTO dto,
                                                 @AuthenticationPrincipal UserDetails loggedInUser) {
        usuarioService.changeOwnPassword(dto, loggedInUser);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }
}