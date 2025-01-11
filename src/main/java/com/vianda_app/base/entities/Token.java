package com.vianda_app.base.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class Token {

    public enum TokenType {
        BEARER
    }

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;
    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Usuario usuario;
}
