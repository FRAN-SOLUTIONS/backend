package com.fran.FRAN.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fran.FRAN.model.entity.Aluno;
//Essa interface é necessária para ter acesso aos métodos do JPA(conexão com BD)
public interface AlunoRepository extends JpaRepository<Aluno, String> {
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByProntuario(String prontuario);
}
