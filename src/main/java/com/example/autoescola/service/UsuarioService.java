package com.example.autoescola.service;

import com.example.autoescola.dto.usuario.UsuarioChangePasswordDTO;
import com.example.autoescola.dto.usuario.UsuarioCreateDTO;
import com.example.autoescola.dto.usuario.UsuarioListDTO;
import com.example.autoescola.dto.usuario.UsuarioUpdateDTO;
import com.example.autoescola.entity.Usuario;
import com.example.autoescola.infra.exception.ValidacaoException;
import com.example.autoescola.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. (Admin) Criar Usuário
    @Transactional
    public Usuario create(UsuarioCreateDTO dto) {
        if (usuarioRepository.findByLogin(dto.login()) != null) {
            throw new ValidacaoException("Login já está em uso.");
        }
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Usuario novoUsuario = new Usuario(dto.login(), senhaCriptografada, dto.role());
        return usuarioRepository.save(novoUsuario);
    }

    // 2. (Admin) Listar Usuários
    public Page<UsuarioListDTO> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioListDTO::new);
    }

    // 3. (Admin) Atualizar Usuário
    @Transactional
    public UsuarioListDTO update(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        // Verifica se o novo login já está em uso por OUTRO usuário
        UserDetails userDetails = usuarioRepository.findByLogin(dto.login());
        if (userDetails != null && !((Usuario)userDetails).getId().equals(id)) {
            throw new ValidacaoException("Login já está em uso por outro usuário.");
        }

        usuario.setLogin(dto.login());
        usuario.setRole(dto.role());
        usuarioRepository.save(usuario);

        return new UsuarioListDTO(usuario);
    }

    // 4. (Admin) Excluir Usuário
    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        // Não permitir que o usuário 'admin' padrão seja excluído
        Usuario usuario = usuarioRepository.findById(id).get();
        if (usuario.getLogin().equals("admin")) {
            throw new ValidacaoException("O usuário 'admin' principal não pode ser excluído.");
        }
        usuarioRepository.deleteById(id);
    }

    // 5. (Qualquer Usuário) Alterar a própria senha
    @Transactional
    public void changeOwnPassword(UsuarioChangePasswordDTO dto, UserDetails loggedInUser) {
        if (loggedInUser == null) {
            throw new ValidacaoException("Usuário não autenticado.");
        }

        Usuario usuario = (Usuario) usuarioRepository.findByLogin(loggedInUser.getUsername());

        // Verifica se a senha atual está correta
        if (!passwordEncoder.matches(dto.senhaAtual(), usuario.getPassword())) {
            throw new ValidacaoException("Senha atual incorreta.");
        }

        // Criptografa e salva a nova senha
        String novaSenhaCriptografada = passwordEncoder.encode(dto.novaSenha());
        usuario.setSenha(novaSenhaCriptografada);
        usuarioRepository.save(usuario);
    }
}