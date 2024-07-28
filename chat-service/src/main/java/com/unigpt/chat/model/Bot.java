package com.unigpt.chat.model;

import com.unigpt.chat.dto.BotEditInfoDTO;

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

    public Bot(Integer id, BotEditInfoDTO dto, User creator) {
        this.id = id;
        this.name = dto.getName();
        this.avatar = dto.getAvatar();
        this.description = dto.getDescription();
        this.isPublished = dto.getIsPublished();
        this.creator = creator;
    }

    public void updateInfo(BotEditInfoDTO dto) {
        this.name = dto.getName();
        this.avatar = dto.getAvatar();
        this.description = dto.getDescription();
        this.isPublished = dto.getIsPublished();
    }

}
