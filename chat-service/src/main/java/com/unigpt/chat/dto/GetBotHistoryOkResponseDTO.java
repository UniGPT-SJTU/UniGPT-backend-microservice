package com.unigpt.chat.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.unigpt.chat.model.History;

import lombok.Data;

@Data
public class GetBotHistoryOkResponseDTO {

    private Integer total;
    private List<HistoryItemDTO> histories;

    public GetBotHistoryOkResponseDTO(Integer total, List<History> histories) {
        this.total = total;
        this.histories = histories.stream().map(HistoryItemDTO::new).collect(Collectors.toList());
    }
}
