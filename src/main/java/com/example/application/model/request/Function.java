package com.example.application.model.request;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Function {
    private String name;
    private String description;

    private Parameters parameters;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    private static class Parameters {
        private String type;
        private JsonNode properties;
        private List<String> required;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public JsonNode getProperties() {
            return properties;
        }

        public void setProperties(JsonNode properties) {
            this.properties = properties;
        }

        public List<String> getRequired() {
            return required;
        }

        public void setRequired(List<String> required) {
            this.required = required;
        }
    }

}
