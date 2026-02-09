package com.apoiaqui.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apoiaqui.backend.common.Pair;
import com.apoiaqui.backend.domain.entity.Account;
import com.apoiaqui.backend.domain.entity.RefreshToken;
import com.apoiaqui.backend.domain.exception.InvalidResourceException;
import com.apoiaqui.backend.domain.exception.NotFoundException;
import com.apoiaqui.backend.domain.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Pair<String, String> signUp(String email, String password, String confirmPassword, String cpf, String firstName, String lastName) {

        if (!password.equals(confirmPassword)) {
            throw new InvalidResourceException("As senhas não coincidem");
        }

        Account account = accountService.create(email, password);
        userService.create(cpf, firstName, lastName, account);

        final String accessToken = jwtService.generateToken(account);
        final String refreshToken = refreshTokenService.create(account).getToken();

        return Pair.of(accessToken, refreshToken);
    }

    public Pair<String, String> signIn(String email, String password) {

        Account account = accountService.findByEmail(email).orElseThrow(() -> new NotFoundException("Email não encontrado"));

        if (!account.matchesPassword(password)) {
            throw new UnauthorizedException("Senha inválida");
        }

        final String accessToken = jwtService.generateToken(account);
        final String refreshToken = refreshTokenService.create(account).getToken();

        return Pair.of(accessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        refreshTokenService.delete(refreshToken);
    }

    public Pair<String, String> refresh(String refreshToken) {
         
        RefreshToken token = refreshTokenService.refresh(refreshToken);

        Account account = token.getAccount();

        final String accessToken = jwtService.generateToken(account);
        final String newRefreshToken = token.getToken();

        return Pair.of(accessToken, newRefreshToken);
    }
    
}
