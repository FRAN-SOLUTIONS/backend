package com.fran.FRAN.service;

import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {  // Nome da Service Layer

    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
