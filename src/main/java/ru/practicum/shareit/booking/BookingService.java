package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking addNewBooking(BookingRequest bookingRequest, Long userId);

    Booking updateBooking(Long bookingId, Long userId, Boolean approved);

    Booking getBookingById(Long bookingId);

    List<BookingDto> getAllBookingsByUserId(Long userId);
}
