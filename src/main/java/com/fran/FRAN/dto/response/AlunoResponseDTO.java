package com.fran.FRAN.dto.response;


import com.fran.FRAN.model.entity.Aluno;
//Apenas formata os dados
public record AlunoResponseDTO(Long id, String nome, String email, String telefone) {
    public AlunoResponseDTO(Aluno aluno){
        this(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getTelefone());
    }
}
