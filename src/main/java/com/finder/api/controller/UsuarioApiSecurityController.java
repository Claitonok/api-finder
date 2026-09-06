package com.finder.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.finder.api.model.FinderUsuarioApiSecurity;
import com.finder.api.service.ServiceUsuarioApiSecurity;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/config") // Rota específica para configuração de segurança
public class UsuarioApiSecurityController {

    private final ServiceUsuarioApiSecurity service;

    UsuarioApiSecurityController(ServiceUsuarioApiSecurity service) {
        this.service = service;
    }

    @PostMapping("/setup-user")
    public ResponseEntity<FinderUsuarioApiSecurity> criarUsuarioAcesso(@RequestBody FinderUsuarioApiSecurity usuario) {

        FinderUsuarioApiSecurity novoUsuario = service.criarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<FinderUsuarioApiSecurity> buscarUsuarioPorUsername(@PathVariable String username) {
        FinderUsuarioApiSecurity usuario = service.buscarUsuarioPorUsername(username);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/verify-user/{username}")
    public ResponseEntity<String> verificarUsuario(@PathVariable String username) {
      
        FinderUsuarioApiSecurity usuario = service.buscarUsuarioPorUsername(username);
        return new ResponseEntity<>("Usuário encontrado: " + usuario.getUsername(), HttpStatus.OK);
      
    }

    @GetMapping("/delete-user/{username}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String username) {
       
        service.deletarUsuario(username);
        return new ResponseEntity<>("Usuário deletado: " + username, HttpStatus.OK);
        
    }
    

}