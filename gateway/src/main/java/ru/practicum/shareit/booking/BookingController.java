package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingRequestDto;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addNewBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @RequestBody @Valid BookingRequestDto requestDto) {
        log.info("Creating booking {}, userId={}", requestDto, userId);
        return bookingClient.addNewBooking(userId, requestDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> updateBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable @Positive long bookingId,
                                                @RequestParam boolean approved) {
        log.info("Update booking {}, userId={}", bookingId, userId);
        return bookingClient.updateBooking(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getByBookingId(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable Long bookingId) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllOwnerBookings(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get bookings from userId={}", userId);
        return bookingClient.getAllOwnerBookings(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserBookings(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get bookings from userId={}", userId);
        return bookingClient.getAllUserBookings(userId);
    }
}