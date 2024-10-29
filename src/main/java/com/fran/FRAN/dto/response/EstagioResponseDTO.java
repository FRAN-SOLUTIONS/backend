package com.fran.FRAN.dto.response;

import java.time.LocalDate;

import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.model.entity.Coordenador;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Orientador;

public record EstagioResponseDTO(Aluno aluno, Orientador orientador, Coordenador coordenador, Integer cargaDiaria, LocalDate dataInicio, LocalDate dataTermino, String status, Boolean obrigatorio) {
    public EstagioResponseDTO(Estagio estagio){
        this(estagio.getAluno(), estagio.getOrientador(), estagio.getCoordenador(), estagio.getCargaDiaria(), estagio.getDataInicio(), estagio.getDataTermino(),
        estagio.getStatus(), estagio.getObrigatorio());
    }
}