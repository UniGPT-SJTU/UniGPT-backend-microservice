package com.unigpt.plugin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer dbId;

    @Column(name = "true_id")
    private Integer trueId;

    @Column(name = "name")
    private String name;

    @Column(name = "account")
    private String account;

    @OneToMany
    @JoinColumn(name = "creator_id")
    private List<Plugin> createPlugins;

    // TODO: Confirm information needed in this microservice

    public User() {
    }

    public User(Integer userid, String username, String account) {
        this.trueId = userid;
        this.name = username;
        this.account = account;
    }
}
