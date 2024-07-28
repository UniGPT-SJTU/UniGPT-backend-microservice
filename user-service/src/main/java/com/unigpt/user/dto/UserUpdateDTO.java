package com.unigpt.user.dto;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String name;
    private String avatar;
    private String description;
}