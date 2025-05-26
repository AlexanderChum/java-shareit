package ru.practicum.shareit.error.exceptions;

public class BookingUnavailableItemException extends RuntimeException {
    public BookingUnavailableItemException(String message) {
        super(message);
    }
}
