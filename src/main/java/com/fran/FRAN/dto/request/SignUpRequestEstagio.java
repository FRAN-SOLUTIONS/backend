package com.fran.FRAN.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestEstagio{ 

   private Boolean obrigatorio;
    private Integer cargaDiaria;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String status;

    private String prontuarioAluno;
    private String prontuarioCoordenador;
}
