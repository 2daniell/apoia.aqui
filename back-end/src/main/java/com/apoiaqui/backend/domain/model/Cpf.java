package com.apoiaqui.backend.domain.model;

import com.apoiaqui.backend.domain.exception.InvalidResourceException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "value")
public class Cpf {

    @Column(name = "cpf", unique = true)
    private String value;

    private Cpf(String cpf) {
        if (!validate(cpf)) {
            throw new InvalidResourceException("CPF inválido");
        }
        this.value = cpf;
    }

    public static Cpf of(String cpf) {
        return new Cpf(cpf);
    }

    private String clean(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    // private booelan validate(String cpf) {
    //     return true;
    // }

    private boolean validate(String cpf) {
        if (cpf == null) return false;

        String cleaned = clean(cpf);

        if (cleaned.length() != 11) return false;

        if (cleaned.chars().distinct().count() == 1) return false;

        return isValidChecksum(cleaned);
    }

    private boolean isValidChecksum(String cpf) {
        try {
            int sum1 = 0;
            for (int i = 0; i < 9; i++) {
                sum1 += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digit1 = (sum1 * 10) % 11;
            if (digit1 == 10) digit1 = 0;

            if (digit1 != Character.getNumericValue(cpf.charAt(9))) return false;

            int sum2 = 0;
            for (int i = 0; i < 10; i++) {
                sum2 += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digit2 = (sum2 * 10) % 11;
            if (digit2 == 10) digit2 = 0;

            return digit2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
