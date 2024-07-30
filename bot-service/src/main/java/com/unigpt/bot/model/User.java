package com.unigpt.bot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany
    @JoinTable(name = "user_like_bot", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "bot_id"))
    private List<Bot> likeBots;

    @ManyToMany
    @JoinTable(name = "user_star_bot", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "bot_id"))
    private List<Bot> starBots;

    @OneToMany(mappedBy = "creator")
    private List<Bot> createBots;


    public User() {
        // not used
    }

    public User(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.likeBots = new ArrayList<>();
        this.starBots = new ArrayList<>();
        this.createBots = new ArrayList<>();
    }

}
