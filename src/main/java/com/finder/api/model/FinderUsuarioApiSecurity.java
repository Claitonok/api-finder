package com.finder.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FinderUsuarioApiSecurity {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    /**
     * O campo "roles" é usado para definir os papéis (roles) do usuário na aplicação.
     * Ele pode ter valores como "ADMIN" ou "USER", que são usados para controlar o acesso às funcionalidades da aplicação.
     */
    @Column(nullable = false)
    private String roles; // "ADMIN" ou "USER"

}
