package com.fran.FRAN.controller;

import com.fran.FRAN.dto.request.LoginRequest;
import com.fran.FRAN.dto.request.SignUpRequest;
import com.fran.FRAN.dto.response.OrientadorResponseDTO;
import com.fran.FRAN.model.entity.Orientador;
import com.fran.FRAN.service.OrientadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("FRAN")
public class OrientadorController { //lida com os mapeamentos das rotas

    @Autowired
    private OrientadorService orientadorService;

    @GetMapping("/orientadores")
    public List<OrientadorResponseDTO> getAllAlunos() {
        return orientadorService.getAllOrientadores();
    }

    @PostMapping("/orientador/signup")
    public ResponseEntity<?> signUpOrientador(@RequestBody @Valid SignUpRequest salvarOrientadorRequest) {
        try {
            Orientador orientador = new Orientador();
            orientador.setProntuario(salvarOrientadorRequest.getProntuario());
            orientador.setNome(salvarOrientadorRequest.getNome());
            orientador.setEmail(salvarOrientadorRequest.getEmail());
            orientador.setTelefone(salvarOrientadorRequest.getTelefone());
            orientador.setSenha(salvarOrientadorRequest.getPassword());

            Orientador savedOrientador = orientadorService.salvarOrientador(orientador);
            return ResponseEntity.ok(savedOrientador);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/orientadores/login")
public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
    try {
        boolean valid = orientadorService.validarSenha(loginRequest.getEmail(), loginRequest.getPassword());
        HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid ? "Login bem-sucedido" : "Senha incorreta.");
    } catch (ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    } catch (Exception e) {
        e.printStackTrace(); // Log do erro completo para análise
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
    }
}

}
