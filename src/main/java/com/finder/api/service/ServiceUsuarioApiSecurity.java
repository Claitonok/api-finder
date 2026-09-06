package com.finder.api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.finder.api.exception.MyRuntimeException;
import com.finder.api.model.FinderUsuarioApiSecurity;
import com.finder.api.repository.RepositoryUsuarioApiSecurity;

@Service
public class ServiceUsuarioApiSecurity {

    private final RepositoryUsuarioApiSecurity repository;

    // O segredo para a autenticação funcionar: BCrypt
    private final BCryptPasswordEncoder passwordEncoder;

    ServiceUsuarioApiSecurity(RepositoryUsuarioApiSecurity repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 
     * @param usuario
     * @return
     */
    public FinderUsuarioApiSecurity criarUsuario(FinderUsuarioApiSecurity usuario) {

        try {
            // Verifica se o usuário já existe
            FinderUsuarioApiSecurity usuarioExistente = repository.findByUsername(usuario.getUsername());
            if (usuarioExistente != null) {
                throw new MyRuntimeException("Usuário já existe: " + usuario.getUsername());
            } else if (usuario.getUsername() == null) {
                throw new MyRuntimeException("O campo 'username' não pode ser nulo.");
            } else if (usuario.getPassword() == null) {
                throw new MyRuntimeException("O campo 'password' não pode ser nulo.");
            } else if (usuario.getRoles() == null) {
                throw new MyRuntimeException("O campo 'roles' não pode ser nulo.");
            }

            // Criptografa a senha antes de enviar para o banco
            String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(senhaCriptografada);

            // Garante que a role tenha o padrão que o Spring espera (opcional, mas
            // recomendado)
            if (!usuario.getRoles().startsWith("ROLE_")) {
                usuario.setRoles(usuario.getRoles().toUpperCase());
            }
            // 2. Validação de Roles (Exemplo de blindagem)
            if (!usuario.getRoles().equals("ADMIN") && !usuario.getRoles().equals("USER")) {
                throw new MyRuntimeException("Role inválida!");
            }

            return repository.save(usuario);
        } catch (MyRuntimeException e) {
            throw new MyRuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    /**
     * Busca um usuário por seu nome de usuário.
     * 
     * @param username
     * @return
     */
    public FinderUsuarioApiSecurity buscarUsuarioPorUsername(String username) {
        FinderUsuarioApiSecurity usuario = repository.findByUsername(username);
        if (usuario == null) {
            throw new MyRuntimeException("Usuário não encontrado: " + username);
        }
        return usuario;
    }

    /**
     * Deleta um usuário por seu nome de usuário.
     * 
     * @param username
     */
    public void deletarUsuario(String username) {
        FinderUsuarioApiSecurity usuario = repository.findByUsername(username);
        if (usuario != null) {
            repository.delete(usuario);
        } else {
            throw new MyRuntimeException("Usuário não encontrado: " + username);
        }
    }

}