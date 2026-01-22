package com.apoiaqui.backend.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apoiaqui.backend.common.Pair;
import com.apoiaqui.backend.controller.auth.request.SigninRequest;
import com.apoiaqui.backend.controller.auth.request.SignupRequest;
import com.apoiaqui.backend.controller.auth.response.AuthResponse;
import com.apoiaqui.backend.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid SignupRequest request, HttpServletResponse response) {

        Pair<String, String> tokens = service.signUp(
            request.email(),
            request.password(),
            request.confirmPassword(), 
            request.cpf(), 
            request.firstName(), 
            request.lastName());

        addRefreshTokenCookie(response, tokens.getRight());

        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(tokens.getLeft()));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid SigninRequest request, HttpServletResponse response) {

        Pair<String, String> tokens = service.signIn(
            request.email(), 
            request.password());

        addRefreshTokenCookie(response, tokens.getRight());

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(tokens.getLeft()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        
        Pair<String, String> tokens = service.refresh(refreshToken);

        addRefreshTokenCookie(response, tokens.getRight());

        return ResponseEntity.ok(new AuthResponse(tokens.getLeft()));
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
