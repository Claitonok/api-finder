package com.finder.api.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finder.api.exception.MyRuntimeException;
import com.finder.api.model.FinderUsuario;
import com.finder.api.repository.RepositoryFinderUsuario;
import com.finder.api.security.JwtService;

@Service
public class ServiceFinderUsuario {

    private final RepositoryFinderUsuario repository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailProducer emailProducer;

    ServiceFinderUsuario(RepositoryFinderUsuario repository, JwtService jwtService,
            BCryptPasswordEncoder passwordEncoder, EmailProducer emailProducer) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailProducer = emailProducer;

    }

    /**
     * Lista todos os usuários.
     * 
     * @return
     */
    public Page<FinderUsuario> listarUsuarios(Pageable pageable) {

        if (repository.count() == 0) {
            FinderUsuario usuarioPadrao = new FinderUsuario();
            usuarioPadrao.setNome("Usuário Padrão");
            usuarioPadrao.setEmail("usuario@padrao.com");

            // 🔐 hash da senha
            usuarioPadrao.setSenha(passwordEncoder.encode("123456")); // Defina a senha padrão aqui
            repository.save(usuarioPadrao);
        }
        return repository.findAll(pageable);
    }

    /**
     * Busca um usuário por ID.
     * Se o usuário não for encontrado, retorna null.
     * 
     * @param id
     * @return
     */
    public FinderUsuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Cria um novo usuário.
     * 
     * @param usuario
     * @return
     */
    public FinderUsuario criarUsuario(FinderUsuario usuario) {

        FinderUsuario usuarioExistente = repository.findByEmail(usuario.getEmail()).orElse(null);

        if (usuarioExistente != null) {
            throw new MyRuntimeException("Email já cadastrado: " + usuario.getEmail());
        }

        // Criptografar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    /**
     * Atualiza um usuário existente.
     * 
     * @param usuario
     * @return
     */
    public FinderUsuario atualizarUsuario(FinderUsuario usuario) {

        FinderUsuario usuarioExistente = repository.findByEmail(usuario.getEmail()).orElse(null);

        if (usuarioExistente == null) {
            throw new MyRuntimeException("Email não encontrado: " + usuario.getEmail());
        }

        // Criptografar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }

    /**
     * Deleta um usuário por ID.
     * 
     * @param id
     */
    public void deletarUsuario(Long id) {
        repository.deleteById(id);
    }

    /**
     * Autentica um usuário com base no email e senha fornecidos.
     * 
     * @param email
     * @param senha
     * @return
     */
    public String loginAutenticarUsuario(String email, String senha) {

        FinderUsuario usuarioOpt = repository.findByEmail(email).orElse(null);

        if (usuarioOpt != null) {
            // Verifica se a senha fornecida corresponde à senha armazenada
            if (passwordEncoder.matches(senha, usuarioOpt.getSenha())) {
                // Gera um token JWT para o usuário autenticado
                return jwtService.generateToken(usuarioOpt.getEmail());
            } else {
                throw new MyRuntimeException("Senha incorreta.");
            }

        } else {
            throw new MyRuntimeException("Usuário não encontrado com o email: " + email);
        }

    }

    /**
     * Busca um usuário por email.
     * 
     * @param email
     * @return
     */
    public FinderUsuario buscarPorEmail(String email) {

        FinderUsuario usuarioExistente = repository.findByEmail(email).orElse(null);
        if (usuarioExistente == null) {
            throw new MyRuntimeException("Email não encontrado: " + email);
        }

        return usuarioExistente;
    }

    /**
     * Recupera a senha de um usuário com base no email fornecido.
     * 
     * @param email
     */
    public FinderUsuario recoverPassword(String email) {

        FinderUsuario usuario = repository.findByEmail(email).orElse(null);

        // 🔐 IMPORTANTE: não revelar se usuário existe
        if (usuario == null) {
            throw new MyRuntimeException("Email não encontrado: " + email);
        }

        // ENVIA PARA FILA (não envia email direto)
        try {
            // Gera um token de recuperação aleatório de 6 dígitos
            String token = String.format("%06d", (int) (Math.random() * 1000000));
            usuario.setResetToken(token);

            // Token expira em 15 minutos
            usuario.setResetTokenExpires(java.time.LocalDateTime.now().plusMinutes(15));
            repository.save(usuario);

            // Enviar email com o token de recuperação (não implementado aqui)
            System.out.println("\nDados do usuário para recuperação de senha:");
            System.out.println("\n======\n");
            System.out.println("Token de recuperação gerado para " + email + ": " + token);
            System.out.println("\n======\n");
            System.out.println("Token expira em: " + LocalDateTime.now().plusMinutes(15));
            System.out.println("\n======\n");

            // Simula o envio para uma fila (ex: RabbitMQ, Kafka, etc.)
            emailProducer.send(usuario.getEmail(), token);
            System.out.println("Token enviado para a fila com sucesso para o email: " + email);
            return usuario;
        } catch (Exception e) {
            System.err.println("Erro ao enviar token para a fila: " + e.getMessage());
            throw new MyRuntimeException("Erro ao enviar token para a fila: " + e.getMessage());
        }

    }

    public void resetPassword(String token, String novaSenha) {

        // 1. Busca o usuário diretamente ou lança exceção se não encontrar
        FinderUsuario usuario = repository.findByResetToken(token)
                .orElseThrow(() -> new MyRuntimeException("Token inválido ou não encontrado."));

        // isAfter: verifica se uma data/hora é posterior.
        // isBefore: verifica se uma data/hora é anterior.
        // isEqual: verifica igualdade.

        // 2. Valida a expiração do token usando isBefore
        if (LocalDateTime.now().isAfter(usuario.getResetTokenExpires())) {
            throw new MyRuntimeException("Token expirado.");
        }

        // 3. Atualiza a senha e invalida o token utilizado
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        
        // ❌ invalidar token o token de recuperação
        usuario.setResetToken(null);
        usuario.setResetTokenExpires(null);

        repository.save(usuario);
    }

}
