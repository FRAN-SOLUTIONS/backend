package com.fran.FRAN.controller;

import java.util.List;
import java.util.Optional;

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
import com.fran.FRAN.dto.request.SignUpRequestAluno;
import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.service.AlunoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("FRAN/alunos")
@CrossOrigin(origins = "http://localhost:5173")
public class AlunoController { //lida com os mapeamentos das rotas

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/")
    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpAluno(@RequestBody @Valid SignUpRequestAluno salvarAlunoRequest) {
        try {
            Aluno aluno = new Aluno();
            aluno.setProntuario(salvarAlunoRequest.getProntuario());
            aluno.setNome(salvarAlunoRequest.getNome());
            aluno.setEmail(salvarAlunoRequest.getEmail());
            aluno.setTelefone(salvarAlunoRequest.getTelefone());
            aluno.setSenha(salvarAlunoRequest.getSenha());
            aluno.setCurso(salvarAlunoRequest.getCurso());
            Aluno savedAluno = alunoService.salvarAluno(aluno);
            return ResponseEntity.ok(savedAluno);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/alunos/login")
public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
    try {
        boolean valid = alunoService.validarSenha(loginRequest.getEmail(), loginRequest.getPassword());
        HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        if (valid) {
            // Armazenar informações do aluno na sessão
            Optional<Aluno> optionalAluno = alunoRepository.findByEmail(loginRequest.getEmail());
            if (optionalAluno.isPresent()) {
                Aluno aluno = optionalAluno.get();  // Extraindo o Aluno do Optional
                session.setAttribute("user", aluno);  // Armazenando o Aluno diretamente na sessão
            }
        }
        return ResponseEntity.status(status).body(valid ? "Login bem-sucedido" : "Senha incorreta.");
    } catch (ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado.");
    }
}


    @GetMapping("/me")
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
