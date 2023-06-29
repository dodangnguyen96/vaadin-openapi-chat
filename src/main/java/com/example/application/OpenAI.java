/*
 * Copyright 2023 Sami Ekblad, Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.example.application;

import com.example.application.model.APIError;
import com.example.application.model.request.ChatRequest;
import com.example.application.model.response.ChatResponse;
import com.example.application.model.Message;
import com.example.application.model.response.Choice;
import com.example.application.model.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Java class to interact with Open AI API.
 */
@Component
@Scope("session")
public class OpenAI {
    @Autowired
    private FunctionSchemaInit functionSchema;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static final String MODEL = "gpt-3.5-turbo";

    private final String apiKey;
    private String latestUserInput;
    private List<Message> messages;

    private List<Message> chatMessages;

    public OpenAI(@Value("${openai.apikey}") String apiKey) {
        this.apiKey = apiKey;
        this.latestUserInput = "";
        this.messages = new ArrayList();
        this.chatMessages = new ArrayList();
    }

    public CompletableFuture<List<Message>> sendAsync(String userInput) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return send(userInput);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void clearAll() {
        this.messages = new ArrayList();
        this.chatMessages = new ArrayList();
    }

    public List<Message> send(String userInput) {
        latestUserInput = userInput;
        messages.add(new Message("user", userInput, Instant.now()));
        chatMessages.add(new Message("user", userInput, Instant.now()));

        try {
            ChatRequest request = new ChatRequest(MODEL, messages, functionSchema.getFunctions());
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(request);

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + this.apiKey);
            conn.setDoOutput(true);


            conn.getOutputStream().write(requestBody.getBytes());

            if (conn.getResponseCode() >= 400) {
                Scanner scanner = new Scanner(conn.getErrorStream());
                StringBuilder responseBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    responseBuilder.append(scanner.nextLine());
                }
                String errorBody = responseBuilder.toString();
                APIError error = objectMapper.readValue(errorBody, APIError.class);
                chatMessages.add(new Message("system", error.getError().getMessage(), Instant.now()));
            } else {
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder responseBuilder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    responseBuilder.append(scanner.nextLine());
                }
                String responseBody = responseBuilder.toString();
                ChatResponse response = objectMapper.readValue(responseBody, ChatResponse.class);
                Choice choice = response.getChoices().get(0);
               
                if (choice.getFinish_reason().equals("function_call")) {
                    messages.add(choice.getMessage());
                    WeatherResponse weatherResponse = new WeatherResponse(WeatherResponse.WeatherUnit.CELSIUS, new Random().nextInt(50), "sunny");
                    messages.add(new Message("function", choice.getMessage().getFunction_call().getName(), objectMapper.writeValueAsString(weatherResponse), Instant.now()));
                    ChatRequest request1 = new ChatRequest(MODEL, messages, functionSchema.getFunctions());
                    String requestBody1 = objectMapper.writeValueAsString(request1);
                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setRequestMethod("POST");
                    conn1.setRequestProperty("Content-Type", "application/json");
                    conn1.setRequestProperty("Authorization", "Bearer " + this.apiKey);
                    conn1.setDoOutput(true);
                    conn1.getOutputStream().write(requestBody1.getBytes());
                    Scanner scanner1 = new Scanner(conn1.getInputStream());
                    StringBuilder responseBuilder1 = new StringBuilder();
                    while (scanner1.hasNextLine()) {
                        responseBuilder1.append(scanner1.nextLine());
                    }
                    String responseBody1 = responseBuilder1.toString();
                    ChatResponse response1 = objectMapper.readValue(responseBody1, ChatResponse.class);
                    messages.add(response1.getChoices().get(0).getMessage());
                    chatMessages.add(response1.getChoices().get(0).getMessage());
                } else {
                    messages.add(choice.getMessage());
                    chatMessages.add(choice.getMessage());
                    
                }
            }
        } catch (Exception e) {
            chatMessages.add(new Message("system", e.getMessage(), Instant.now()));
        }
        return chatMessages;
    }
}