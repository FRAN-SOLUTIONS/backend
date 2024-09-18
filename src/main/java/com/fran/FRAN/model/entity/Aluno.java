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
@EqualsAndHashCode(of = "id")
public class Aluno {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String password;
}
