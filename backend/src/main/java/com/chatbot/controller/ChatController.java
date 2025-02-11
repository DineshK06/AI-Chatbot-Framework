package com.chatbot.controller;


import com.chatbot.model.ChatMessage;
import com.chatbot.model.ChatRequest;
import com.chatbot.model.ChatResponse;
import com.chatbot.repository.ChatMessageRepository;
import com.chatbot.service.ChatbotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatbotService chatbotService;
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatbotService chatbotService, ChatMessageRepository chatMessageRepository) {
        this.chatbotService = chatbotService;
        this.chatMessageRepository = chatMessageRepository;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) throws JsonProcessingException {
        return chatbotService.getChatResponse(chatRequest.getMessage());
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory() {
        return chatMessageRepository.findAll(Sort.by(Sort.Direction.ASC, "timestamp"));
    }
}
