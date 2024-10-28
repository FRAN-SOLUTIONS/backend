package com.fran.FRAN.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fran.FRAN.model.entity.Coordenador;
import com.fran.FRAN.model.entity.Orientador;

public interface CoordenadorRepository extends JpaRepository<Coordenador, String> {
    Optional<Orientador> findByEmail(String email); 
    Optional<Orientador> findByProntuario(String prontuario);
}
