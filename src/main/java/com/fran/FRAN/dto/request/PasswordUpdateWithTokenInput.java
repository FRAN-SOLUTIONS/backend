package com.fran.FRAN.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class PasswordUpdateWithTokenInput {
    @NotBlank
    private String password;

    @NotBlank
    private String token;
}