package com.fran.FRAN.dto.response;

import com.fran.FRAN.model.entity.Relatorio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioResponseDTO {
    private Integer codigo;
    private String mesReferencia;
    private LocalTime horasTotais;
    private LocalTime horasValidas;
    private LocalDateTime dataEntregue;
    private String status;

    public RelatorioResponseDTO(Relatorio relatorio) {
        this.codigo = relatorio.getCodigo();
        this.mesReferencia = relatorio.getMes_referencia().name();
        this.horasTotais = relatorio.getHoras_totais();
        this.horasValidas = relatorio.getHoras_validas();
        this.dataEntregue = relatorio.getData_entregue();
        this.status = relatorio.getStatus().name();
    }
}

