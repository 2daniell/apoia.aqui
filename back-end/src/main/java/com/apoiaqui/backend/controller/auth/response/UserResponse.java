package com.apoiaqui.backend.controller.auth.response;

public record UserResponse(
    Long id,
    String name,
    String email
) {
    
}
