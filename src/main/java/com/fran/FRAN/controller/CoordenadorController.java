package com.fran.FRAN.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fran.FRAN.dto.request.LoginRequest;
import com.fran.FRAN.dto.request.SignUpRequestCoordenador;
import com.fran.FRAN.dto.response.CoordenadorResponseDTO;
import com.fran.FRAN.model.dao.CoordenadorRepository;
import com.fran.FRAN.model.entity.Coordenador;
import com.fran.FRAN.service.CoordenadorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("FRAN/coordenadores")
@CrossOrigin(origins = "http://localhost:8080")
public class CoordenadorController { // Lida com os mapeamentos das rotas

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @GetMapping("/")
    public List<CoordenadorResponseDTO> getAllCoordenadores() {
        return coordenadorService.getAllCoordenadores();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpCoordenador(@RequestBody @Valid SignUpRequestCoordenador salvarCoordenadorRequest) {
        try {
            Coordenador coordenador = new Coordenador();
            coordenador.setProntuario(salvarCoordenadorRequest.getProntuario());
            coordenador.setNome(salvarCoordenadorRequest.getNome());
            coordenador.setEmail(salvarCoordenadorRequest.getEmail());
            coordenador.setSenha(salvarCoordenadorRequest.getPassword());

            Coordenador savedCoordenador = coordenadorService.salvarCoordenador(coordenador);
            return ResponseEntity.ok(savedCoordenador);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession session) {
        try {
            boolean valid = coordenadorService.validarSenha(loginRequest.getProntuario(), loginRequest.getPassword());
            HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
            if (valid) {
                // Armazenar informações do coordenador na sessão
                Optional<Coordenador> optionalCoordenador = coordenadorRepository.findByProntuario(loginRequest.getProntuario());
                if (optionalCoordenador.isPresent()) {
                    Coordenador coordenador = optionalCoordenador.get();
                    session.setAttribute("coordenador", coordenador);
                }
            }
            return ResponseEntity.status(status).body(valid ? "Login bem-sucedido" : "Senha incorreta.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            e.printStackTrace(); // Log do erro completo para análise
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<CoordenadorResponseDTO> getLoggedUser(HttpSession session) {
        Coordenador coordenador = (Coordenador) session.getAttribute("coordenador");
        if (coordenador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(new CoordenadorResponseDTO(coordenador));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}
