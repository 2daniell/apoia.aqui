package com.apoiaqui.backend.service;

import org.springframework.stereotype.Service;

import com.apoiaqui.backend.domain.entity.Account;
import com.apoiaqui.backend.domain.entity.User;
import com.apoiaqui.backend.domain.exception.InvalidResourceException;
import com.apoiaqui.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(String cpf, String firstName, String lastName, Account account) {

        if (repository.existsByCpfValue(cpf)) {
            throw new InvalidResourceException("CPF já registrado.");
        }

        User user = new User(cpf);
        user.setAccount(account);

        return repository.save(user);
    }


    
}
