package com.finder.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finder.api.model.FinderUsuario;

public interface RepositoryFinderUsuario extends JpaRepository<FinderUsuario, Long> {

    Optional<FinderUsuario> findByEmail(String email);
    
    Optional<FinderUsuario> findByEmailAndSenha(String email, String senha);

    Optional<FinderUsuario> findByResetToken(String resetToken);

    Optional<FinderUsuario> findByEmailAndResetToken(String email, String resetToken);


}
