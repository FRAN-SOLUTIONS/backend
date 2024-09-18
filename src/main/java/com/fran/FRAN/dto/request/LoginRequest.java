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
public class LoginRequest {
    //lida com os dados vindo de login
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Senha é obrigatório")
    private String password;
}

