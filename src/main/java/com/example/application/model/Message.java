package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;


public class Message {

    @JsonIgnore
    private Instant time;
    private String role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FunctionCall function_call;

    public Message() {
        this.time = Instant.now();
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
        this.time = Instant.now();
    }

    public Message(String role, String content, Instant time) {
        this.role = role;
        this.content = content;
        this.time = time;
    }

    public Message(String role, String name, String content, Instant time) {
        this.time = time;
        this.role = role;
        this.name = name;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTime() {
        return time;
    }

    public FunctionCall getFunction_call() {
        return function_call;
    }

    public void setFunction_call(FunctionCall function_call) {
        this.function_call = function_call;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class FunctionCall {
        private String name;
        private String arguments;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArguments() {
            return arguments;
        }

        public void setArguments(String arguments) {
            this.arguments = arguments;
        }
    }
}
