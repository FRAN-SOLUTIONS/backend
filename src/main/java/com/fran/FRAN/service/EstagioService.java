package com.fran.FRAN.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fran.FRAN.dto.request.SignUpRequestEmpresa;
import com.fran.FRAN.dto.request.SignUpRequestEstagio;
import com.fran.FRAN.dto.response.EstagioResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.dao.EstagioRepository;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.dao.EmpresaRepository;
import com.fran.FRAN.model.dao.CursoRepository;
import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.model.entity.Curso;
import com.fran.FRAN.model.entity.Empresa;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Orientador;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    public EstagioResponseDTO criarEstagio(SignUpRequestEstagio signUpRequestEstagio, String prontuarioOrientador) {
        SignUpRequestEmpresa empresaDTO = signUpRequestEstagio.getEmpresa();
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresa.setRazaoSocial(empresaDTO.getRazaoSocial());
        empresa.setCnpj(empresaDTO.getCnpj());
        empresa.setEmail(empresaDTO.getEmail());
        empresa.setTelefone(empresaDTO.getTelefone());
        empresa = empresaRepository.save(empresa);
        
        Orientador orientador = orientadorRepository.findByProntuario(prontuarioOrientador)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orientador não encontrado"));
    
        Aluno aluno = alunoRepository.findByProntuario(signUpRequestEstagio.getProntuarioAluno())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
      
        Estagio estagio = new Estagio();
        estagio.setObrigatorio(signUpRequestEstagio.getObrigatorio());
        estagio.setCargaDiaria(signUpRequestEstagio.getCargaDiaria());
        estagio.setDataInicio(signUpRequestEstagio.getDataInicio());
        estagio.setDataTermino(signUpRequestEstagio.getDataTermino());
        estagio.setStatus(signUpRequestEstagio.getStatus());
        estagio.setAluno(aluno);
        estagio.setOrientador(orientador);
        estagio.setEmpresa(empresa);

   
        Estagio estagioSalvo = estagioRepository.save(estagio);
        return new EstagioResponseDTO(estagioSalvo);
    }

    public List<EstagioResponseDTO> getEstagiosByOrientadorProntuario(String prontuarioOrientador) {
        return estagioRepository.findByOrientadorProntuario(prontuarioOrientador).stream()
                .map(EstagioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<EstagioResponseDTO> getAllEstagios() {
        return estagioRepository.findAll().stream()
                .map(EstagioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<Curso> getAllCursos(){
        return cursoRepository.findAll().stream()
        .collect(Collectors.toList());
    }
}
