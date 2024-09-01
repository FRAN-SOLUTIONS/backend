package com.fran.FRAN.controller;

import com.fran.FRAN.dto.request.LoginRequest;
import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("FRAN")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/alunos")
    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @PostMapping("/alunos/salvar")
    public ResponseEntity<Aluno> salvarAluno(@RequestBody Aluno aluno) {
        Aluno savedAluno = alunoService.salvarAluno(aluno);
        return ResponseEntity.ok(savedAluno);
    }

    @PostMapping("/alunos/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean valid = alunoService.validarSenha(loginRequest.getEmail(), loginRequest.getPassword());
            HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(status).body(valid ? "Login bem-sucedido" : "Senha incorreta.");
        } catch (ResponseStatusException e) {
            // Tratar erros específicos do serviço
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // Tratar erros genéricos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado.");
        }
    }
}
