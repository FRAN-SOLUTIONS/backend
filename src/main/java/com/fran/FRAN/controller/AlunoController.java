package com.fran.FRAN.controller;

import com.fran.FRAN.dto.request.LoginRequest;
import com.fran.FRAN.dto.request.SignUpRequest;
import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("FRAN")
public class AlunoController { //lida com os mapeamentos das rotas

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/alunos")
    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @PostMapping("/alunos/signup")
    public ResponseEntity<?> signUpAluno(@RequestBody @Valid SignUpRequest salvarAlunoRequest) {
        try {
            Aluno aluno = new Aluno();
            aluno.setNome(salvarAlunoRequest.getNome());
            aluno.setEmail(salvarAlunoRequest.getEmail());
            aluno.setTelefone(salvarAlunoRequest.getTelefone());
            aluno.setPassword(salvarAlunoRequest.getPassword());

            Aluno savedAluno = alunoService.salvarAluno(aluno);
            return ResponseEntity.ok(savedAluno);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/alunos/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean valid = alunoService.validarSenha(loginRequest.getEmail(), loginRequest.getPassword());
            HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(status).body(valid ? "Login bem-sucedido" : "Senha incorreta.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado.");
        }
    }
}
