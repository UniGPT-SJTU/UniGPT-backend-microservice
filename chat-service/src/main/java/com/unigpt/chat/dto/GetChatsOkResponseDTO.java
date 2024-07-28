package com.unigpt.chat.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetChatsOkResponseDTO {

    private Integer total;
    private List<ChatDTO> chats;

    public GetChatsOkResponseDTO(Integer total, List<ChatDTO> chats) {
        this.total = total;
        this.chats = chats;
    }
}
