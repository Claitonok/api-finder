package com.finder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finder.api.model.FinderUsuarioApiSecurity;

public interface RepositoryUsuarioApiSecurity extends JpaRepository<FinderUsuarioApiSecurity, Integer> {

    FinderUsuarioApiSecurity findByUsername(String username);

}