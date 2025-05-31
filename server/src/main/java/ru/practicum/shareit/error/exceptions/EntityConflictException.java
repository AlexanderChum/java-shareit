package ru.practicum.shareit.error.exceptions;

public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}
