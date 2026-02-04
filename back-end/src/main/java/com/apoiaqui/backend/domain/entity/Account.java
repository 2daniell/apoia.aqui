package com.apoiaqui.backend.domain.entity;

import com.apoiaqui.backend.domain.model.Email;
import com.apoiaqui.backend.domain.model.Password;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
public final class Account {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password; 

    @OneToOne 
    @Getter
    @JoinColumn(name = "user_id")
    private User user;

    public Account(String email, String password) {
        this(Email.of(email), Password.of(password));
    }

    public Account(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.getValue();
    }
    
    public boolean matchesPassword(String value) {
        return password.matches(value);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
