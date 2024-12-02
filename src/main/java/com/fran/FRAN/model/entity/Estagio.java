package com.fran.FRAN.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "estagio")
public class Estagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false)
    private Boolean obrigatorio;

    @Column(name = "carga_diaria", nullable = false)
    private Integer cargaDiaria;

    
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @FutureOrPresent
    @Column(name = "data_termino", nullable = false)
    private LocalDate dataTermino;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // Relacionamento com Aluno
    @ManyToOne
    @JoinColumn(name = "prontuario_aluno", referencedColumnName = "prontuario", nullable = false)
    private Aluno aluno;

    // Relacionamento com Orientador
    @ManyToOne
    @JoinColumn(name = "prontuario_professor", referencedColumnName = "prontuario", nullable = false)
    private Orientador orientador;

    @OneToMany(mappedBy = "estagio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relatorio> relatorios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "codigoEmpresa", referencedColumnName = "codigo", nullable = false)
    private Empresa empresa;

    // Relacionamento com Coordenador
    //@ManyToOne
    //@JoinColumn(name = "prontuario_coordenador", referencedColumnName = "prontuario", nullable = false)
    //private Coordenador coordenador;
}
