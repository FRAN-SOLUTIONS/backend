package com.fran.FRAN.dto.response;


import com.fran.FRAN.model.entity.Aluno;
//Apenas formata os dados
public record AlunoResponseDTO(String prontuario, String nome, String email, String telefone, String curso) {
    public AlunoResponseDTO(Aluno aluno){
        this(aluno.getProntuario(), aluno.getNome(), aluno.getEmail(), aluno.getTelefone(), aluno.getCurso());
    }
}
