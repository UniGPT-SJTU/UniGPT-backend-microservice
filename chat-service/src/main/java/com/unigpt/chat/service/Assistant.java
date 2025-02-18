package com.unigpt.chat.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;


public interface Assistant {
    TokenStream chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
