package com.unigpt.bot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "plugin")
public class Plugin {

    @Id
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar")
    private String avatar;

    // 哪些机器人使用了这个插件
    @ManyToMany(mappedBy = "plugins")
    private List<Bot> bots;

    public Plugin() {
        // not used
    }

    public Plugin(Integer id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.bots = new ArrayList<>();
    }

}
