package com.unigpt.user.dto;

import com.unigpt.user.model.User;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private String name;
    private String avatar;

    public UserUpdateRequestDTO(User user) {
        this.name = user.getName();
        this.avatar = user.getAvatar();
    }
}

