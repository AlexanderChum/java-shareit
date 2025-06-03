package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;

import java.util.List;

import static ru.practicum.shareit.constants.Constants.REQUEST_HEADER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> addNewBooking(@RequestBody BookingRequest bookingRequest,
                                                    @RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Поступил запрос на добавление букинга");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingService.addNewBooking(bookingRequest, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> updateBooking(@RequestHeader(name = REQUEST_HEADER_ID) Long userId,
                                                    @PathVariable Long bookingId,
                                                    @RequestParam("approved") Boolean approved) {
        log.info("Поступил запрос на обновление букинга");
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, userId, approved));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getByBookingId(@PathVariable Long bookingId) {
        log.info("Поступил запрос на получение букинга");
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getAllOwnerBookings(@RequestHeader(name = REQUEST_HEADER_ID) Long ownerId) {
        log.info("Поступил запрос на получение всех букингов по владельцу");
        return ResponseEntity.ok(bookingService.getAllBookingsByUserId(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllUserBookings(@RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Поступил запрос на получение всех букингов пользователя");
        return ResponseEntity.ok(bookingService.getAllBookingsByUserId(userId));
    }
}
