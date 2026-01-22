package com.apoiaqui.backend.domain.entity;

import com.apoiaqui.backend.domain.model.Cpf;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Cpf cpf;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

    public User(String cpf) {
        this(Cpf.of(cpf));
    }

    public User(Cpf cpf) {
        this.cpf = cpf;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.setUser(this);
    }

}
