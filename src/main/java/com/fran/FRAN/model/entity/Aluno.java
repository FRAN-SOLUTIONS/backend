package com.fran.FRAN.model.entity;

import jakarta.persistence.*;
import lombok.*;

//Foi criada a tabela Aluno invés de usuario pois o desenvolvimento das drotas já havia iniciado antes da tarefa ser pedida
@Table(name = "aluno")
@Entity(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "prontuario")
public class Aluno {
    @Id
    private String prontuario;
    private String nome;
    private String email;
    private String telefone;
    private String password;
}
