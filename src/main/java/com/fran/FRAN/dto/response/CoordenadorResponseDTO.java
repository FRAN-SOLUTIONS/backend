package com.fran.FRAN.dto.response;

import com.fran.FRAN.model.entity.Coordenador;

public record CoordenadorResponseDTO(String prontuario, String nome, String email) {
    public CoordenadorResponseDTO(Coordenador coordenador) {
        this(coordenador.getProntuario(), coordenador.getNome(), coordenador.getEmail());
    }
}
