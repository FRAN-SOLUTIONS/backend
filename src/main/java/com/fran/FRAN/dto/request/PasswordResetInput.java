package com.fran.FRAN.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class PasswordResetInput {
    @Email
    @NotBlank
    private String email;
}