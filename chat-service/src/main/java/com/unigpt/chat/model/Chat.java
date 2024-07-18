package com.unigpt.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chat")
public class Chat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private History history;

    @Column(name = "type")
    private ChatType type;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "is_visible")
    private Boolean isVisible;

    public Chat() {
        // not used
    }

    public Chat(History history, ChatType type, String content, Boolean isVisible) {
        this.history = history;
        this.type = type;
        this.content = content;
        this.isVisible = isVisible;
    }

}
