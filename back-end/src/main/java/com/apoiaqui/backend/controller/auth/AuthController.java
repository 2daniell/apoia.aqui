package com.apoiaqui.backend.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apoiaqui.backend.common.Pair;
import com.apoiaqui.backend.controller.auth.request.SigninRequest;
import com.apoiaqui.backend.controller.auth.request.SignupRequest;
import com.apoiaqui.backend.controller.auth.response.AuthResponse;
import com.apoiaqui.backend.controller.auth.response.UserResponse;
import com.apoiaqui.backend.domain.entity.Account;
import com.apoiaqui.backend.domain.entity.User;
import com.apoiaqui.backend.domain.exception.NotFoundException;
import com.apoiaqui.backend.service.AccountService;
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
    private final AccountService accountService;

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

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) {
        
        if (refreshToken != null) {
            service.logout(refreshToken);
        }

        removeRefreshTokenCookie(response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Jwt jwt) {

        Long subject = Long.parseLong(jwt.getSubject());
        String email = jwt.getClaimAsString("email");

        Account account = accountService.findById(subject).orElseThrow(() -> new NotFoundException("User not found"));
        User user = account.getUser();

        return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user.getId(), user.getFirstName(), email));
    }   

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    private void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
