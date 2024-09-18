package com.fran.FRAN.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fran.FRAN.dto.request.LoginRequest;
import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.service.AlunoService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("FRAN")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private AlunoRepository alunoRepository;

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
public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
    try {
        boolean valid = alunoService.validarSenha(loginRequest.getEmail(), loginRequest.getPassword());
        if (valid) {
            // Armazenar informações do usuário na sessão
            Aluno aluno = alunoRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Aluno não encontrado."));
            session.setAttribute("user", aluno);
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado.");
    }
}

@GetMapping("/alunos/me")
public ResponseEntity<AlunoResponseDTO> getLoggedUser(HttpSession session) {
    Aluno aluno = (Aluno) session.getAttribute("user");
    if (aluno == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    return ResponseEntity.ok(new AlunoResponseDTO(aluno));
}


@GetMapping("/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate();
    return ResponseEntity.ok("Logout realizado com sucesso");
}

}
