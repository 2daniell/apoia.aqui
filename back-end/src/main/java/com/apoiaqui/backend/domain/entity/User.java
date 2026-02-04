package com.apoiaqui.backend.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.apoiaqui.backend.domain.model.Cpf;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor

public class User {

    @Id
    @Getter
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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Campaign> campaigns;

    public User(String cpf, String firstName,  String lastName) {
        this(Cpf.of(cpf), firstName, lastName);
        this.campaigns = new ArrayList<>();
    }

    public User(Cpf cpf, String firstName,  String lastName) {
        this.cpf = cpf;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.setUser(this);
    }

    public void addCampaign(Campaign campaign) {
        campaigns.add(campaign);
        campaign.setOwner(this);
    }

}
