package com.unigpt.user.model;

import com.unigpt.user.dto.BotEditInfoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Comment> comments;
//
//    // 该机器人使用哪些插件
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "bot_use_plugin", joinColumns = @JoinColumn(name = "bot_id"), inverseJoinColumns = @JoinColumn(name = "plugin_id"))
//    private List<Plugin> plugins;

    public Bot(BotEditInfoDTO dto) {
        this.name = dto.getName();
        this.avatar = dto.getAvatar();
        this.description = dto.getDescription();
//        this.isPublished = dto.isPublished();
//        this.detail = dto.getDetail();
//        this.photos = dto.getPhotos();
//        this.isPrompted = dto.isPrompted();
//        this.promptKeys = dto.getPromptKeys();
//        this.likeNumber = 0;
//        this.starNumber = 0;
//        this.likeUsers = new ArrayList<>();
//        this.starUsers = new ArrayList<>();
//        this.comments = new ArrayList<>();
//
//        this.llmArgs = LLMArgs.builder()
//                .baseModelType(BaseModelType.fromValue(dto.getBaseModelAPI()))
//                .temperature(dto.getTemperature()).build();

    }
//
//    public void updateInfo(BotEditInfoDTO dto) {
//        this.name = dto.getName();
//        this.avatar = dto.getAvatar();
//        this.description = dto.getDescription();
//        this.isPublished = dto.isPublished();
//        this.detail = dto.getDetail();
//        this.photos = dto.getPhotos();
//        this.isPrompted = dto.isPrompted();
//        this.promptKeys = dto.getPromptKeys();
//
//        this.llmArgs = LLMArgs
//                .builder()
//                .baseModelType(BaseModelType.fromValue(dto.getBaseModelAPI()))
//                .temperature(dto.getTemperature())
//                .build();
//    }

    public Bot() {
        // not used
    }
}