package com.fran.FRAN.model.dao;

import com.fran.FRAN.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Essa interface é necessária para ter acesso aos métodos do JPA(conexão com BD)
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByEmail(String email); // Metodo para buscar aluno pelo email
}
