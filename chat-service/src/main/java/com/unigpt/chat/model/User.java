package com.unigpt.chat.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany
    private List<History> histories;

    public User() {
        // not used
    }

    public User(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.histories = new ArrayList<>();
    }

}
