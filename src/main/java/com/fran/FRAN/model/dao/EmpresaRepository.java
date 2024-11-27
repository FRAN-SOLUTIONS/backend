package com.fran.FRAN.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fran.FRAN.model.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
    Optional<Empresa> findByCnpj(String cnpj);
}
