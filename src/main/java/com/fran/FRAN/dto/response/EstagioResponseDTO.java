package com.fran.FRAN.dto.response;

import java.time.LocalDate;

import com.fran.FRAN.model.entity.Aluno;
import com.fran.FRAN.model.entity.Empresa;
import com.fran.FRAN.model.entity.Estagio;
import com.fran.FRAN.model.entity.Orientador;

public record EstagioResponseDTO(Aluno aluno, Orientador orientador, Empresa empresa, Integer cargaDiaria, LocalDate dataInicio, LocalDate dataTermino, String status, Boolean obrigatorio) {
    public EstagioResponseDTO(Estagio estagio){
        this(estagio.getAluno(), estagio.getOrientador(), estagio.getEmpresa(), estagio.getCargaDiaria(), estagio.getDataInicio(), estagio.getDataTermino(),
        estagio.getStatus().toString(), estagio.getObrigatorio());
    }
}