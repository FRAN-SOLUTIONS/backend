package com.fran.FRAN.dto.request;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestEmpresa {
  
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nomeFantasia;

    @NotBlank(message = "Razão social é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String razaoSocial;
    
    @CNPJ
    private String cnpj;
 
    @Email(message = "E-mail deve ser válido")
    private String email;
  
    private String telefone;
}
