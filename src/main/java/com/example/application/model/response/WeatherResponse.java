package com.example.application.model.response;

public class WeatherResponse {
    private WeatherUnit unit;
    private int temperature;
    private String description;

    public WeatherResponse(WeatherUnit unit, int temperature, String description) {
        this.unit = unit;
        this.temperature = temperature;
        this.description = description;
    }

    public WeatherUnit getUnit() {
        return unit;
    }

    public void setUnit(WeatherUnit unit) {
        this.unit = unit;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum WeatherUnit {
        CELSIUS, FAHRENHEIT;
    }
}
