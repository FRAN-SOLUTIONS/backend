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

import com.fran.FRAN.dto.request.SignUpRequestEstagio;
import com.fran.FRAN.dto.response.EstagioResponseDTO;
import com.fran.FRAN.model.dao.EstagioRepository;
import com.fran.FRAN.model.entity.Curso;
import com.fran.FRAN.model.entity.Orientador;
import com.fran.FRAN.service.EstagioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("FRAN/estagios")
@CrossOrigin(origins = "http://localhost:5173")
public class EstagioController {

    @Autowired
    private EstagioService estagioService;
    @Autowired
    private EstagioRepository estagioRepository;

    @GetMapping("/")
    public List<EstagioResponseDTO> getAllEstagios() {
        return estagioService.getAllEstagios();
    }

    @PostMapping("/create")
    public ResponseEntity<EstagioResponseDTO> createEstagio(
            @Valid @RequestBody SignUpRequestEstagio signUpRequestEstagio, HttpSession session) {

        Orientador orientador = (Orientador) session.getAttribute("orientador");

        if (orientador == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Orientador não está autenticado");
        }

        EstagioResponseDTO estagioResponse = estagioService.criarEstagio(signUpRequestEstagio,
                orientador.getProntuario());

        return new ResponseEntity<>(estagioResponse, HttpStatus.CREATED);
    }

    @GetMapping("/orientador/estagios")
    public ResponseEntity<List<EstagioResponseDTO>> getEstagiosByOrientador(HttpSession session) {
        Orientador orientador = (Orientador) session.getAttribute("orientador");

        if (orientador == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Orientador não está autenticado");
        }

        List<EstagioResponseDTO> estagios = estagioService
                .getEstagiosByOrientadorProntuario(orientador.getProntuario());
        return ResponseEntity.ok(estagios);
    }

    @GetMapping("/cursos")
    public List<Curso> getAllCursos() {
        return estagioService.getAllCursos();
    }

}
