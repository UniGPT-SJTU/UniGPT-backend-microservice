package com.unigpt.user.dto;
import java.util.List;
import java.util.stream.Collectors;

import com.unigpt.user.model.Bot;
import lombok.Data;

@Data
public class BotEditInfoDTO {

  private String name;
  private String avatar;
  private String description;
  public BotEditInfoDTO() {
    // not used
  }
}
