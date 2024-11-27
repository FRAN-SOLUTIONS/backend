package com.fran.FRAN.dto.request;

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

  @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "Razão social é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String razaoSocial;
    
    @NotBlank(message = "cnpj é obrigatório")
    private String cnpj;

    @NotBlank(message = "Email é obrigatória")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
}
