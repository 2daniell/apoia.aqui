package com.apoiaqui.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TesteController {

    @GetMapping
    public String test(@AuthenticationPrincipal Jwt jwt) {

        String userId = jwt.getSubject();
        String userEmail = jwt.getClaimAsString("email");

        return "User id: " + userId + ", User email: " + userEmail;
    }
    
}
