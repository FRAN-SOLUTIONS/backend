package com.fran.FRAN.model.dao;

import com.fran.FRAN.model.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
}
