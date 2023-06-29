package com.example.application;


import com.example.application.model.request.Function;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FunctionSchemaInit {
    public static List<Function> functions;


    public FunctionSchemaInit() {
        this.functions = new ArrayList<>();
    }

    @PostConstruct
    private void FunctionSchemaInit() {
        ObjectMapper objectMapper = new ObjectMapper();
        File directory = new File("src/main/resources/function-schema");

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
            if (files != null) {
                for (File file : files) {
                    try {
                        Function Function = objectMapper.readValue(file, Function.class);
                        functions.add(Function);
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Invalid directory path: " + directory.getAbsolutePath());
        }
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
