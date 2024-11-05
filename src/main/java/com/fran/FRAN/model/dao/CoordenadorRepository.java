package com.fran.FRAN.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fran.FRAN.model.entity.Coordenador;

public interface CoordenadorRepository extends JpaRepository<Coordenador, String> {
    Optional<Coordenador> findByEmail(String email); 
    Optional<Coordenador> findByProntuario(String prontuario);
}
