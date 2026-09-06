package com.finder.api.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.finder.api.exception.MyRuntimeException;
import com.finder.api.model.FinderUsuarioApiSecurity;
import com.finder.api.repository.RepositoryUsuarioApiSecurity;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final RepositoryUsuarioApiSecurity repositoryUsuariopiSecurity;

    public MyUserDetailsService(RepositoryUsuarioApiSecurity repositoryUsuariopiSecurity) {
        this.repositoryUsuariopiSecurity = repositoryUsuariopiSecurity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws MyRuntimeException {

            FinderUsuarioApiSecurity usuarioApiSecurity = repositoryUsuariopiSecurity.findByUsername(username);
            
            if (usuarioApiSecurity == null) {
                throw new MyRuntimeException("Usuário não encontrado: " + username);
            }

            System.out.println("\n=== Detalhes do usuário para autenticação ===\n");
            System.out.println("Usuário encontrado: " + usuarioApiSecurity.getUsername());
            System.out.println("Senha: " + usuarioApiSecurity.getPassword());
            System.out.println("Roles do usuário: " + usuarioApiSecurity.getRoles());

           // Constrói um UserDetails usando os dados do usuário encontrado
            return User.builder()
                    .username(usuarioApiSecurity.getUsername())
                    .password(usuarioApiSecurity.getPassword())
                    //.password(new BCryptPasswordEncoder().encode("0147")) // Codifica a senha "0147" para comparação
                    .roles(usuarioApiSecurity.getRoles()) // Adiciona o prefixo ROLE_ aqui
                    .build();
    }
}
