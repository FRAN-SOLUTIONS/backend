package com.fran.FRAN.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Interessado {
    private String nome;
    private String email;
    @JsonIgnore
    private String senha;
}
