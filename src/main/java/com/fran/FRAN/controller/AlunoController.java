package com.fran.FRAN.controller;

import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/hello")
    public String getSetence() {
        return "Hello World!";
    }
}