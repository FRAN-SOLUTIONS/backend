package com.fran.FRAN.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orientador")
public class Orientador extends Interessado {
    @Id
    private String prontuario;


    public void setProntuario(String prontuario) {
        this.prontuario = prontuario != null ? prontuario.toLowerCase() : null;
    }
}
