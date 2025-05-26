package ru.practicum.shareit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.error.exceptions.BookingUnavailableItemException;
import ru.practicum.shareit.error.exceptions.BookingUpdateAccessRejectedException;
import ru.practicum.shareit.error.exceptions.EntityConflictException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.error.model.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        log.debug("Ошибка валидации");
        return new ErrorResponse("Ошибка валидации", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(EntityNotFoundException e) {
        log.debug("Ошибка при поиске сущности");
        return new ErrorResponse("Объект не найден", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEntityConflictException(EntityConflictException e) {
        log.debug("Ошибка при обработке сущности");
        return new ErrorResponse("Ошибка при обработке объекта", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookingUnavailableItem(BookingUnavailableItemException e) {
        log.debug("Ошибка при проверке доступности для букирования");
        return new ErrorResponse("Предмет не доступен для букирования", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleBookingUpdateAccessRejected(BookingUpdateAccessRejectedException e) {
        log.debug("Ошибка при проверке доступа");
        return new ErrorResponse("Доступ запрещен", e.getMessage());
    }
}
