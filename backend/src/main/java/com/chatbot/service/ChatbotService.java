package com.chatbot.service;

import com.chatbot.model.ChatMessage;
import com.chatbot.model.ChatResponse;
import com.chatbot.repository.ChatMessageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final ChatMessageRepository chatMessageRepository;
    @Value("${openai.api.key}")
    private String apiKey;

    public ChatbotService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    
    @PostConstruct
    public void loadApiKey() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("OPENAI_API_KEY");
        logger.info("API Key loaded successfully");
    }

    public ChatResponse getChatResponse(String message) {
        logger.info("Received message: {}", message);
        saveChatMessage("user", message);

        //Construct OpenAI request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", message)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Configure RestTemplate with timeouts
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000); // 5 seconds
        requestFactory.setReadTimeout(5000); // 5 seconds

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        try {
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, request, String.class);

            // Extract AI response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody =
                    objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            List<Map<String, Object>> choices =
                    objectMapper.convertValue(responseBody.get("choices"), new TypeReference<>() {});
            Map<String, Object> messageMap =
                    objectMapper.convertValue(choices.getFirst().get("message"), new TypeReference<>() {});
            String botResponse = (String) messageMap.get("content");

            saveChatMessage("bot", botResponse);
            logger.info("Bot response: {}", botResponse);
            return new ChatResponse(botResponse);
        } catch (HttpClientErrorException e) {
            logger.error("Client error while fetching response from OpenAI: {}", e.getMessage());
            return new ChatResponse("Client error occurred while fetching response from OpenAI");
        } catch (HttpServerErrorException e) {
            logger.error("Server error while fetching response from OpenAI: {}", e.getMessage());
            return new ChatResponse("Server error occurred while fetching response from OpenAI");
        } catch (ResourceAccessException e) {
            logger.error("Timeout error while fetching response from OpenAI: {}", e.getMessage());
            return new ChatResponse("Timeout error occurred while fetching response from OpenAI");
        } catch (Exception e) {
            logger.error("Error occurred while fetching response from OpenAI", e);
            return new ChatResponse("Error occurred while fetching response from OpenAI");
        }
    }

    private void saveChatMessage(String user, String message) {
        logger.info("Saving chat message to database");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(user);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(Instant.now().toString());
        chatMessageRepository.save(chatMessage);
    }
}
