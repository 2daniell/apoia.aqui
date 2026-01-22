package com.apoiaqui.backend.controller.auth.request;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(

    @NotBlank
    String cpf,

    @NotBlank
    String firstName,

    @NotBlank
    String lastName,

    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotBlank
    String confirmPassword
    
) {}
