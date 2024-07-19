package com.unigpt.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetUsersOkResponseDTO {

    private List<UserBriefInfoDTO> users;
    private Integer total;

    public GetUsersOkResponseDTO(List<UserBriefInfoDTO> users, Integer total) {
        this.users = users;
        this.total = total;
    }

    public GetUsersOkResponseDTO() {
    }

    public GetUsersOkResponseDTO(Integer total, List<UserBriefInfoDTO> users) {
        this.users = users;
        this.total = total;
    }

}
