package com.fran.FRAN.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetInput {
    @NotBlank
    private String prontuario;
}