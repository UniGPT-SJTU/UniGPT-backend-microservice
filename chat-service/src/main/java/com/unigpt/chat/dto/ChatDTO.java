package com.unigpt.chat.dto;

import com.unigpt.chat.model.Chat;
import com.unigpt.chat.model.ChatType;
import com.unigpt.chat.model.History;

import lombok.Data;

@Data
public class ChatDTO {

    private Integer id;
    private String content;
    private String avatar;
    private String name;
    private ChatType type;

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.content = chat.getContent();
        this.type = chat.getType();

        History history = chat.getHistory();
        switch (chat.getType()) {
            case USER:
                this.avatar = history.getUser().getAvatar();
                this.name = history.getUser().getName();
                break;
            case BOT:
                this.avatar = history.getBot().getAvatar();
                this.name = history.getBot().getName();
                break;

            case SYSTEM:
            default:
                this.avatar = "";
                this.name = "System";
                break;
        }
    }
}
