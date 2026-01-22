package com.apoiaqui.backend.controller.auth.request;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
    @NotBlank
    String email,

    @NotBlank
    String password
) {}
