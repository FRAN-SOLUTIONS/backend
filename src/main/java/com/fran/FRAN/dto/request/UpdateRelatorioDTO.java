package com.fran.FRAN.dto.request;

import com.fran.FRAN.model.entity.Mes;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.fran.FRAN.model.entity.Status;

@Data
public class UpdateRelatorioDTO{
    private Long id;
    private LocalTime horasTotais;
    private LocalTime horasValidas;
    private LocalDateTime dataEntregue;
    private Mes mesReferencia;
    private Status status;
}
