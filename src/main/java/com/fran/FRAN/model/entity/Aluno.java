package com.fran.FRAN.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aluno")
public class Aluno extends Interessado {
    @Id
    private String prontuario;
    private String telefone;
    private String curso;
}
