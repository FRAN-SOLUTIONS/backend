package com.fran.FRAN.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestEstagio { 

    @NotNull(message = "O campo 'obrigatorio' não pode ser nulo")
    private Boolean obrigatorio;

    @NotNull(message = "O campo 'cargaDiaria' não pode ser nulo")
    @Min(value = 3, message = "A carga diária deve ser no mínimo 3 hora")
    @Max(value = 14, message = "A carga diária não pode ser maior que 14 horas")
    private Integer cargaDiaria;

    @NotNull(message = "A data de início não pode ser nula")
    @PastOrPresent(message = "A data de início deve ser uma data passada ou atual")
    private LocalDate dataInicio;

    @NotNull(message = "A data de término não pode ser nula")
    @FutureOrPresent(message = "A data de término deve ser uma data futura ou atual")
    private LocalDate dataTermino;

    private String status;

    @NotBlank(message = "O prontuário do aluno não pode estar em branco")
    @Size(min = 4, max = 14, message = "O prontuário do aluno deve ter entre 4 e 14 caracteres")
    private String prontuarioAluno;

    @NotNull(message = "Os dados da empresa são obrigatórios")
    private SignUpRequestEmpresa empresa;

}
