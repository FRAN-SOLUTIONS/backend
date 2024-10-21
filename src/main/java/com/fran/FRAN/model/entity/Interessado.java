package com.fran.FRAN.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Interessado {
    private String nome;
    private String email;
    private String senha;
}
