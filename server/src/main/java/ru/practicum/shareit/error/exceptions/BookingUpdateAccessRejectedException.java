package ru.practicum.shareit.error.exceptions;

public class BookingUpdateAccessRejectedException extends RuntimeException {
    public BookingUpdateAccessRejectedException(String message) {
        super(message);
    }
}
