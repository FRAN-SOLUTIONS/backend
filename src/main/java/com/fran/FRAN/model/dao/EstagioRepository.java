package com.fran.FRAN.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fran.FRAN.dto.request.SignUpRequestEstagio;
import com.fran.FRAN.model.entity.Estagio;

public interface EstagioRepository extends JpaRepository<Estagio, Long> {
@Query(value = """
        SELECT 
            e.codigo AS codigoEstagio,
            e.obrigatorio AS obrigatorio,
            e.cargaDiaria AS cargaDiaria,
            e.dataInicio AS dataInicio,
            e.dataTermino AS dataTermino,
            e.status AS status,
            a.prontuario AS prontuarioAluno,
            a.nome AS nomeAluno,
            o.prontuario AS prontuarioOrientador,
            o.nome AS nomeOrientador
        FROM 
            Estagio e
        JOIN 
            Aluno a ON e.aluno.prontuario = a.prontuario
        JOIN 
            Orientador o ON e.orientador.prontuario = o.prontuario
    """, nativeQuery = true)
    List<SignUpRequestEstagio> findEstagioWithAlunoAndOrientador();
}
