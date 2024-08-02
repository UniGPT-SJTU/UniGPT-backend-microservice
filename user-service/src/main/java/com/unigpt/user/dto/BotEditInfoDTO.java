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
//  private int baseModelAPI;
//  private boolean isPublished;
//  private String detail;
//  private List<String> photos;
//  private boolean isPrompted;
//  private List<PromptChatDTO> promptChats;
//  private List<String> promptKeys;
//  private double temperature;
//  private List<PluginBriefInfoDTO> plugins;

  public BotEditInfoDTO() {
    // not used
  }
}
