package com.example.chatbot.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class LLMClient {

    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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";

    public String callLLM(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("HTTP-Referer", "http://localhost");
        headers.set("X-Title", "TestApp");

        // 2. Create body
        Map<String, Object> body = new HashMap<>();
        body.put("model", "openai/gpt-4o");
        body.put("max_tokens", 1024);

        List<Map<String, String>> messages = new ArrayList<>();
        //Add system prompt
        messages.add(Map.of(
                "role", "system",
                "content", "You are a strict academic assistant for students. Your sole purpose is to help with study-related topics such as Math, Science, English, History, Geography, Academic writing, and Exam preparation. If the user asks anything unrelated to studies (e.g. games, jokes, dating, entertainment, random chit-chat), respond strictly with: \"I'm here to help you focus on studies. Let’s stay on track!\" Always keep responses short, clear, and focused on learning."
        ));

        //Add user message
        messages.add(Map.of(
                "role", "user",
                "content", userMessage
        ));
        //It is send as list of maps because To give accurate, helpful responses, an LLM needs to see the entire conversation history, not just a single message.

        body.put("messages", messages);

        // 3. Wrap request
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 4. Send request
        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, requestEntity, Map.class);

        // 5. Extract reply
        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } else {
            throw new RuntimeException("LLM Error: " + response.getStatusCode());
        }
    }
}

