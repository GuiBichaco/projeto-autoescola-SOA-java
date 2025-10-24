package com.example.autoescola.dto.usuario;

import com.example.autoescola.entity.Usuario;
import com.example.autoescola.enums.UsuarioRole;

// DTO para listar usuários (sem senha)
public record UsuarioListDTO(
        Long id,
        String login,
        UsuarioRole role
) {
    public UsuarioListDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }
}