package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.constants.BookingStatus;
import ru.practicum.shareit.error.exceptions.BookingUnavailableItemException;
import ru.practicum.shareit.itemAndComment.ItemRepository;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User owner = new User(1L, "Owner", "owner@ya.ru");
    private User booker = new User(2L, "Booker", "booker@ya.ru");
    private Item testItem = new Item(1L, "Test Item", "Test Description", true, null,
            owner, new ItemRequest());
    private BookingRequest testBookingRequest = new BookingRequest(1L, LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2));
    private Item unavailableItem = new Item(1L, "Item1", "Description1", false,
            null, new User(), new ItemRequest());

    private Booking createTestBooking(BookingStatus status) {
        Item item = new Item(1L, "Item1", "Description1", true, null,
                owner, new ItemRequest());

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStart(LocalDateTime.now().plusDays(1));
        booking.setEnd(LocalDateTime.now().plusDays(2));
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(status);
        return booking;
    }

    @Test
    void addNewBookingValidDataShouldReturnBookingDto() {
        Booking booking = createTestBooking(BookingStatus.WAITING);
        BookingDto expectedDto = new BookingDto();
        expectedDto.setStatus(BookingStatus.WAITING);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(testItem));
        when(bookingMapper.toBooking(any(), any(), any())).thenReturn(booking);
        when(bookingRepository.save(any())).thenReturn(booking);
        when(bookingMapper.toBookingDto(any())).thenReturn(expectedDto);

        BookingDto result = bookingService.addNewBooking(testBookingRequest, booker.getId());

        assertThat(result.getStatus()).isEqualTo(BookingStatus.WAITING);
        verify(bookingRepository).save(booking);
    }

    @Test
    void addNewBookingUnavailableItemShouldThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(unavailableItem));

        assertThrows(BookingUnavailableItemException.class,
                () -> bookingService.addNewBooking(testBookingRequest, booker.getId()));

        verify(bookingRepository, never()).save(any());
    }

    @Test
    void updateBookingApproveBookingShouldChangeStatus() {
        Booking booking = createTestBooking(BookingStatus.WAITING);
        Booking updatedBooking = createTestBooking(BookingStatus.APPROVED);
        BookingDto expectedDto = new BookingDto();
        expectedDto.setStatus(BookingStatus.APPROVED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(bookingMapper.updateBookingStatus(any(), any())).thenReturn(updatedBooking);
        when(bookingMapper.toBookingDto(any())).thenReturn(expectedDto);

        BookingDto result = bookingService.updateBooking(1L, owner.getId(), true);

        assertThat(result.getStatus()).isEqualTo(BookingStatus.APPROVED);
    }

    @Test
    void getBookingByIdExistingBookingShouldReturnDto() {
        Booking booking = createTestBooking(BookingStatus.APPROVED);
        BookingDto expectedDto = new BookingDto();
        expectedDto.setId(1L);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toBookingDto(any())).thenReturn(expectedDto);

        BookingDto result = bookingService.getBookingById(booking.getId());

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getAllBookingsByUserIdShouldReturnBookings() {
        Booking booking = createTestBooking(BookingStatus.APPROVED);
        BookingDto bookingDto = new BookingDto();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(bookingRepository.findAllByUserId(anyLong())).thenReturn(List.of(booking));
        when(bookingMapper.toBookingDtoList(any())).thenReturn(List.of(bookingDto));

        List<BookingDto> result = bookingService.getAllBookingsByUserId(booker.getId());

        assertThat(result).hasSize(1);
    }
}