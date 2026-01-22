package com.apoiaqui.backend.domain.model;

import com.apoiaqui.backend.domain.exception.InvalidResourceException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable 
@NoArgsConstructor
@EqualsAndHashCode(of = "value")
public final class Email {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Getter
    @Column(name = "email", unique = true)
    private String value;

    private Email(String email) {
        if (!validate(email)) throw new InvalidResourceException("Email invalido");
        this.value = email;
    }

    private boolean validate(String value) {
        if (value == null || value.isBlank()) return false;
        return value.matches(EMAIL_REGEX);
    }

    public static Email of(String value) {
        return new Email(value);
    }
    
}
