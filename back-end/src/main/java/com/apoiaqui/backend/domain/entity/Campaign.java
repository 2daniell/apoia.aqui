package com.apoiaqui.backend.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "campaigns")
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private User owner;

    private Integer donations;

    @Column(nullable = false)
    private BigDecimal goal;

    @Column(nullable = false)
    private BigDecimal raised;

    public Campaign(String title, String description, BigDecimal goal, User user) {
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.donations = 0;
        this.owner = user;
        this.goal = goal;
        this.raised = BigDecimal.ZERO;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public boolean isOwner(Long userId) {
        if (userId == null ) return false;
        return owner.getId().equals(userId);
    }

}
