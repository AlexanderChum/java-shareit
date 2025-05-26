package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.constants.BookingStatus;
import ru.practicum.shareit.error.exceptions.*;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "ru.practicum.shareit")
@ActiveProfiles("test")
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingServiceImplTest {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private UserRepository userRepository;

    private BookingRequest validBookingRequest;

    @BeforeEach
    void setUp() {
        validBookingRequest = new BookingRequest();
        validBookingRequest.setItemId(1L);
        validBookingRequest.setStart(LocalDateTime.now().plusDays(1));
        validBookingRequest.setEnd(LocalDateTime.now().plusDays(2));
    }

    @Test
    void addNewBookingValidDataShouldReturnBookingDto() {
        BookingDto bookingDto = bookingService.addNewBooking(validBookingRequest, 2L);

        assertThat(bookingDto.getId()).isNotNull();
        assertThat(bookingDto.getStatus()).isEqualTo(BookingStatus.WAITING);
    }

    @Test
    void addNewBookingUnavailableItemShouldThrowException() {
        validBookingRequest.setItemId(2L); // Item2 with false status

        assertThrows(BookingUnavailableItemException.class,
                () -> bookingService.addNewBooking(validBookingRequest, 2L));
    }

    @Test
    void updateBookingApproveBookingShouldChangeStatus() {
        BookingDto updated = bookingService.updateBooking(1L, 1L, true);

        assertThat(updated.getStatus()).isEqualTo(BookingStatus.APPROVED);
    }

    @Test
    void getBookingByIdExistingBookingShouldReturnDto() {
        BookingDto bookingDto = bookingService.getBookingById(1L);

        assertThat(bookingDto.getId()).isEqualTo(1L);
        assertThat(bookingDto.getItem().getName()).isEqualTo("Item1");
    }

    @Test
    void getAllBookingsByUserIdShouldReturnTwoBookings() {
        List<BookingDto> bookings = bookingService.getAllBookingsByUserId(2L);

        assertThat(bookings).hasSize(2);
    }
}