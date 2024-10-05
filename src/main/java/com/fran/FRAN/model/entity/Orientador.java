package com.fran.FRAN.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "orientador")
@Entity(name = "orientador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "prontuario")
public class Orientador {
    @Id
    private String prontuario;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
}
