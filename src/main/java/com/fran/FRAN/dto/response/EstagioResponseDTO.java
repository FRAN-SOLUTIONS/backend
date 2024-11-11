package com.fran.FRAN.dto.response;

import java.time.LocalDate;

import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Orientador;

public record EstagioResponseDTO(Aluno aluno, Orientador orientador, Integer cargaDiaria, LocalDate dataInicio, LocalDate dataTermino, String status, Boolean obrigatorio) {
    public EstagioResponseDTO(Estagio estagio){
        this(estagio.getAluno(), estagio.getOrientador(), estagio.getCargaDiaria(), estagio.getDataInicio(), estagio.getDataTermino(),
        estagio.getStatus(), estagio.getObrigatorio());
    }
}