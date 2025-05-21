package ru.practicum.shareit.constants.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingRequest;

public class StartBeforeEndValidator
        implements ConstraintValidator<StartBeforeEnd, BookingRequest> {

    @Override
    public boolean isValid(BookingRequest bookingRequest, ConstraintValidatorContext context) {
        if ((bookingRequest.getStart() == null) || (bookingRequest.getEnd() == null)) return true;

        return bookingRequest.getEnd().isAfter(bookingRequest.getStart());
    }
}