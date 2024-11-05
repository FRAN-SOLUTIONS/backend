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
import com.fran.FRAN.dto.request.SignUpRequestOrientador;
import com.fran.FRAN.dto.response.OrientadorResponseDTO;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.entity.Orientador;
import com.fran.FRAN.service.OrientadorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("FRAN/orientadores")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials="true")
public class OrientadorController { //lida com os mapeamentos das rotas

    @Autowired
    private OrientadorService orientadorService;
    @Autowired
    private OrientadorRepository orientadorRepository;

    @GetMapping("/")
    public List<OrientadorResponseDTO> getAllAlunos() {
        return orientadorService.getAllOrientadores();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpOrientador(@RequestBody @Valid SignUpRequestOrientador salvarOrientadorRequest) {
        try {
            Orientador orientador = new Orientador();
            orientador.setProntuario(salvarOrientadorRequest.getProntuario());
            orientador.setNome(salvarOrientadorRequest.getNome());
            orientador.setEmail(salvarOrientadorRequest.getEmail());
            orientador.setSenha(salvarOrientadorRequest.getPassword());

            Orientador savedOrientador = orientadorService.salvarOrientador(orientador);
            return ResponseEntity.ok(savedOrientador);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/login")
public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession session) {
    try {
        boolean valid = orientadorService.validarSenha(loginRequest.getProntuario(), loginRequest.getPassword());
        HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        if (valid) {
            // Armazenar informações do orientador na sessão
            Optional<Orientador> optionalOrientador = orientadorRepository.findByProntuario(loginRequest.getProntuario());
            if (optionalOrientador.isPresent()) {
                Orientador orientador = optionalOrientador.get();  // Extraindo o Aluno do Optional
                session.setAttribute("orientador", orientador);  // Armazenando o Aluno diretamente na sessão
                return ResponseEntity.status(status).body("Bem-vindo, " + orientador.getNome());
            }
        }
        return ResponseEntity.status(status).body(valid ? "Login bem-sucedidoBACK" : "Senha incorretaBACK.");
    } catch (ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    } catch (Exception e) {
        e.printStackTrace(); // Log do erro completo para análise
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }  
    }

    @GetMapping("/me")
public ResponseEntity<OrientadorResponseDTO> getLoggedUser(HttpSession session) {
    Orientador orientador = (Orientador) session.getAttribute("orientador");
    if (orientador == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    return ResponseEntity.ok(new OrientadorResponseDTO(orientador));
}


@GetMapping("/logout")
public ResponseEntity<String> logout(HttpSession session) {
    session.invalidate();
    return ResponseEntity.ok("Logout realizado com sucesso");
}

}
