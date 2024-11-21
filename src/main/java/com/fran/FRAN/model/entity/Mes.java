package com.fran.FRAN.model.entity;

import lombok.Getter;

public enum Mes {
    JAN("Janeiro", 31),
    FEV("Fevereiro", 28), // Pode variar em anos bissextos
    MAR("Março", 31),
    ABR("Abril", 30),
    MAI("Maio", 31),
    JUN("Junho", 30),
    JUL("Julho", 31),
    AGO("Agosto", 31),
    SET("Setembro", 30),
    OUT("Outubro", 31),
    NOV("Novembro", 30),
    DEZ("Dezembro", 31);

    @Getter
    private final String nomeCompleto;
    private final int dias;

    Mes(String nomeCompleto, int dias) {
        this.nomeCompleto = nomeCompleto;
        this.dias = dias;
    }

    public int getDias(boolean anoBissexto) {
        if (this == FEV && anoBissexto) {
            return 29; // Fevereiro tem 29 dias em anos bissextos
        }
        return dias;
    }
}
