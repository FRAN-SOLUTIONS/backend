package com.fran.FRAN.dto.response;

import com.fran.FRAN.model.entity.Orientador;

public record OrientadorResponseDTO(String prontuario, String nome, String email, String telefone) {
    public OrientadorResponseDTO(Orientador orientador) {
        this(orientador.getProntuario(), orientador.getNome(), orientador.getEmail(), orientador.getTelefone());
    }
}
