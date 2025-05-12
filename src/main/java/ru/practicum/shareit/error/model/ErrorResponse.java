package ru.practicum.shareit.error.model;

public class ErrorResponse {
    private final String message;
    private final String description;

    public ErrorResponse(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
