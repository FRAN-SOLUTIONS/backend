package com.fran.FRAN.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fran.FRAN.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import com.fran.FRAN.dto.request.SignUpRequestEstagio;
import com.fran.FRAN.dto.response.EstagioResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.dao.EstagioRepository;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.dao.RelatorioRepository;

@Service
public class EstagioService {
    @Autowired
    private EstagioRepository estagioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Autowired
    private RelatorioRepository relatorioRepository;

    public EstagioResponseDTO criarEstagio(SignUpRequestEstagio signUpRequestEstagio, String prontuarioOrientador) {
        Aluno aluno = alunoRepository.findByProntuario(signUpRequestEstagio.getProntuarioAluno())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Orientador orientador = orientadorRepository.findByProntuario(prontuarioOrientador)
                .orElseThrow(() -> new RuntimeException("Orientador não encontrado"));

        Estagio estagio = new Estagio();
        estagio.setObrigatorio(signUpRequestEstagio.getObrigatorio());
        estagio.setCargaDiaria(signUpRequestEstagio.getCargaDiaria());
        estagio.setDataInicio(signUpRequestEstagio.getDataInicio());
        estagio.setDataTermino(signUpRequestEstagio.getDataTermino());
        estagio.setStatus(Status.PENDENTE);
        estagio.setAluno(aluno);
        estagio.setOrientador(orientador);

        Estagio savedEstagio = estagioRepository.save(estagio);

        List<Relatorio> relatorios = new ArrayList<>();

        for (Mes mes : Mes.values()) {
            Relatorio relatorio = new Relatorio();
            relatorio.setMes_referencia(mes);
            relatorio.setData_entregue(LocalDateTime.now());
            relatorio.setHoras_totais(LocalTime.ofSecondOfDay(0)); // inicia com 0
            relatorio.setHoras_validas(LocalTime.ofSecondOfDay(0));
            relatorio.setStatus(Status.PENDENTE);
            relatorio.setEstagio(savedEstagio);

            relatorios.add(relatorio);
        }

        relatorioRepository.saveAll(relatorios);
        return new EstagioResponseDTO(savedEstagio);
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
}
