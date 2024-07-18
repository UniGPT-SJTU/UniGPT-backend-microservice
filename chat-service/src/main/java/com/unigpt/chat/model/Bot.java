package com.unigpt.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bot")
public class Bot {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Bot() {
        // not used
    }

}
