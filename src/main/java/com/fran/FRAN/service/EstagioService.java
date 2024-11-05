package com.fran.FRAN.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fran.FRAN.dto.request.SignUpRequestEstagio;
import com.fran.FRAN.dto.response.EstagioResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.dao.CoordenadorRepository;
import com.fran.FRAN.model.dao.EstagioRepository;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.model.entity.Coordenador;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Orientador;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    public EstagioResponseDTO criarEstagio(SignUpRequestEstagio signUpRequestEstagio, String prontuarioOrientador) {
        Aluno aluno = alunoRepository.findByProntuario(signUpRequestEstagio.getProntuarioAluno())
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
  
        Coordenador coordenador = coordenadorRepository.findByProntuario(signUpRequestEstagio.getProntuarioCoordenador())
            .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
        Orientador orientador = orientadorRepository.findByProntuario(prontuarioOrientador)
            .orElseThrow(() -> new RuntimeException("Orientador não encontrado"));

        Estagio estagio = new Estagio();
        estagio.setObrigatorio(signUpRequestEstagio.getObrigatorio());
        estagio.setCargaDiaria(signUpRequestEstagio.getCargaDiaria());
        estagio.setDataInicio(signUpRequestEstagio.getDataInicio());
        estagio.setDataTermino(signUpRequestEstagio.getDataTermino());
        estagio.setStatus(true);
        estagio.setAluno(aluno);
        estagio.setOrientador(orientador);
        estagio.setCoordenador(coordenador);
      
        Estagio savedEstagio = estagioRepository.save(estagio);
        // Converter o Estagio salvo em EstagioResponseDTO e retornar
        return new EstagioResponseDTO(savedEstagio);
    }

    public List<EstagioResponseDTO> getAllEstagios() {
        return estagioRepository.findAll().stream()
                .map(EstagioResponseDTO::new)
                .collect(Collectors.toList());
    }

}
