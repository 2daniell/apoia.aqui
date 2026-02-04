package com.apoiaqui.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apoiaqui.backend.domain.entity.Account;
import com.apoiaqui.backend.domain.exception.InvalidResourceException;
import com.apoiaqui.backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account create(String email, String password) {

        if (repository.existsByEmailValue(email)) {
            throw new InvalidResourceException("Email já registrado.");
        }

        Account account = new Account(email, password);

        return repository.save(account);
    }

    public Optional<Account> findByEmail(String email) {
        return repository.findByEmailValue(email);
    }

    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
    
}
