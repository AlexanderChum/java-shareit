package ru.practicum.shareit.constants.timeValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

public class StartBeforeEndValidator
        implements ConstraintValidator<StartBeforeEnd, BookingRequestDto> {

    @Override
    public boolean isValid(BookingRequestDto bookingRequest, ConstraintValidatorContext context) {
        if ((bookingRequest.getStart() == null) || (bookingRequest.getEnd() == null)) return true;

        return bookingRequest.getEnd().isAfter(bookingRequest.getStart());
    }
}