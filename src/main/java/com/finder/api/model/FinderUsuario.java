package com.finder.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FinderUsuario {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expires")
    private LocalDateTime resetTokenExpires;

}
