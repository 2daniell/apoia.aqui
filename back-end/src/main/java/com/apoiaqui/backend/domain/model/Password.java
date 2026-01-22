package com.apoiaqui.backend.domain.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.apoiaqui.backend.domain.exception.InvalidResourceException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable 
@NoArgsConstructor
@EqualsAndHashCode(of = "value")
public final class Password {

    private static final String PASSWORD_REGEX ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_=+]).{6,}$";

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Getter
    @Column(name = "password")
    private String value;

    private Password(String password) {
        if (!validate(password)) throw new InvalidResourceException("A senha precisa ter pelo menos 6 digitos e um caracter especial");
        this.value = passwordEncoder.encode(password);
    }

    private boolean validate(String value) {
        return value != null && value.matches(PASSWORD_REGEX);
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public boolean matches(String password) {
        return passwordEncoder.matches(password, value);
    }

}
