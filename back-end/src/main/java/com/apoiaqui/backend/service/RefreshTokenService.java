package com.apoiaqui.backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apoiaqui.backend.domain.entity.Account;
import com.apoiaqui.backend.domain.entity.RefreshToken;
import com.apoiaqui.backend.domain.exception.UnauthorizedException;
import com.apoiaqui.backend.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_DURATION_SEC = 7 * 24 * 60 * 60;

    private final RefreshTokenRepository repository;

    @Transactional
    public RefreshToken refresh(String oldToken) {

        RefreshToken token = findByToken(oldToken);

        if (isExpired(token)) {
            repository.delete(token);
            throw new UnauthorizedException("Token expirado.");
        }

        String newRefreshToken = UUID.randomUUID().toString();

        token.setExpiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_DURATION_SEC));
        token.setToken(newRefreshToken);

        return repository.save(token);
    }

    @Transactional
    public RefreshToken create(Account account) {

        String newRefreshToken = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
            .account(account)
            .expiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_DURATION_SEC))
            .token(newRefreshToken)
            .build();

        return repository.save(token);
    }

    @Transactional
    public void delete(String refreshToken) {
        repository.deleteByToken(refreshToken);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public RefreshToken findByToken(String token) {
        return repository.findByToken(token).orElseThrow(() -> new UnauthorizedException("Token invalido."));
    }

    
}
