package com.finder.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finder.api.dto.ForgotPasswordRequestDTO;
import com.finder.api.model.FinderUsuario;
import com.finder.api.service.ServiceFinderUsuario;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users") // Rota específica para gerenciamento de usuários
public class FinderUsuarioController {

    private final ServiceFinderUsuario serviceFinderUsuario;

    FinderUsuarioController(ServiceFinderUsuario serviceFinderUsuario) {
        this.serviceFinderUsuario = serviceFinderUsuario;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<FinderUsuario>> listarUsuarios(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        // GET /usuarios/list?page=0&size=10
        // GET /usuarios/list?page=1&size=20
        // GET /usuarios/list?page=0&size=10&sort=nome,desc

        Page<FinderUsuario> paginaUsuarios = serviceFinderUsuario.listarUsuarios(pageable);

        return new ResponseEntity<>(paginaUsuarios, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FinderUsuario> buscarUsuarioPorId(@PathVariable Long id) {

        FinderUsuario usuario = serviceFinderUsuario.buscarPorId(id);

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<FinderUsuario> cadastrarUsuario(@RequestBody FinderUsuario usuario) {

        FinderUsuario novoUsuario = serviceFinderUsuario.criarUsuario(usuario);

        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<FinderUsuario> atualizarUsuario(@RequestBody FinderUsuario usuario) {
        FinderUsuario usuarioAtualizado = serviceFinderUsuario.atualizarUsuario(usuario);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        serviceFinderUsuario.deletarUsuario(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    @PostMapping("/authenticate/{email}/{senha}")
    public ResponseEntity<?> autenticarUsuario(@PathVariable String email, @PathVariable String senha) {

        
        String usuarioAutenticado = serviceFinderUsuario.loginAutenticarUsuario(email, senha);
        
        // Buscar o usuário pelo email Para obter o ID do usuário
        FinderUsuario usuario = serviceFinderUsuario.buscarPorEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Usuário autenticado com sucesso!");
        response.put("Token", usuarioAutenticado);
        response.put("id", usuario.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(serviceFinderUsuario.buscarPorEmail(email));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> recoverarSenha(@RequestBody ForgotPasswordRequestDTO request) {

        FinderUsuario usuario = serviceFinderUsuario.recoverPassword(request.getEmail());

        Map<String, Object> respoRecoMap = new HashMap<>();
        respoRecoMap.put("success", true);
        respoRecoMap.put("message", "Código enviado com sucesso");
        respoRecoMap.put("resetToken", usuario.getResetToken());

        return new ResponseEntity<>(respoRecoMap, HttpStatus.OK);

    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetarSenha(@RequestBody FinderUsuario usuario) {

        serviceFinderUsuario.resetPassword(usuario.getResetToken(), usuario.getSenha());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Senha resetada com sucesso!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
