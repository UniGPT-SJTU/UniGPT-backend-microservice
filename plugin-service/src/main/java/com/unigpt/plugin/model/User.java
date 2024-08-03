package com.unigpt.plugin.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(name = "avatar")
    private String avatar;

    // TODO: Confirm information needed in this microservice

    public User() {
    }
}
