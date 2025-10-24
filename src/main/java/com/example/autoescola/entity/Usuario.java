package com.example.autoescola.entity;

import com.example.autoescola.enums.UsuarioRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    // Construtor para novos usuários (usado no service)
    public Usuario(String login, String senhaCriptografada, UsuarioRole role) {
        this.login = login;
        this.senha = senhaCriptografada;
        this.role = role;
    }

    // Setter para senha (usado na troca de senha)
    public void setSenha(String senhaCriptografada) {
        this.senha = senhaCriptografada;
    }

    // Setter para atualização (usado por admin)
    public void setLogin(String login) {
        this.login = login;
    }

    // Setter para atualização (usado por admin)
    public void setRole(UsuarioRole role) {
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Agora o papel é dinâmico
        if (this.role == UsuarioRole.ROLE_ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}