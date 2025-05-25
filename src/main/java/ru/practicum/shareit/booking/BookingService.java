package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;

import java.util.List;

public interface BookingService {
    BookingDto addNewBooking(BookingRequest bookingRequest, Long userId);

    BookingDto updateBooking(Long bookingId, Long userId, Boolean approved);

    BookingDto getBookingById(Long bookingId);

    List<BookingDto> getAllBookingsByUserId(Long userId);
}
