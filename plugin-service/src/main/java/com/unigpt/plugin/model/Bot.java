package com.unigpt.plugin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "bot")
public class Bot {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer dbId;

    @Column(name = "true_id")
    private Integer trueId;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bot_use_plugin", joinColumns = @JoinColumn(name = "bot_id"), inverseJoinColumns = @JoinColumn(name = "plugin_id"))
    private List<Plugin> plugins;

    public Bot() {
    }
}