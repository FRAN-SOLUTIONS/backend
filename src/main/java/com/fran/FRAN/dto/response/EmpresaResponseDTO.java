package com.fran.FRAN.dto.response;

import com.fran.FRAN.model.entity.Empresa;

public record EmpresaResponseDTO(Integer codigo, String nomeFantasia, String razaoSocial, String cnpj, String telefone, String email) {
    public EmpresaResponseDTO(Empresa empresa) {
        this(empresa.getCodigo(), empresa.getNomeFantasia(), empresa.getRazaoSocial(), empresa.getCnpj(), empresa.getTelefone(), empresa.getEmail());
    }
}
