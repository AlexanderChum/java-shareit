package ru.practicum.shareit.booking;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.constants.BookingStatus;
import ru.practicum.shareit.error.exceptions.BookingUnavailableItemException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.itemAndComment.ItemRepository;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public BookingDto addNewBooking(BookingRequest bookingRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));
        Item item = itemRepository.findById(bookingRequest.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));

        log.info("Проверка доступности вещи для букирования");
        if (!item.getAvailable()) throw new BookingUnavailableItemException("Букинг данной вещи недоступен");

        return bookingMapper.toBookingDto(bookingRepository.save(bookingMapper.toBooking(bookingRequest, user, item)));
    }

    public BookingDto updateBooking(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Букинг не найден"));

        User user = userRepository.findById(userId) //та самая заглушка
                .orElseThrow(() -> new ValidationException("Такого пользователя не найдено"));
        //нужен конкретный код ошибки под данное исключение

        log.info("Получен запрос в сервис на обновление статуса букинга");
        BookingStatus status = approved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        Booking bookingToUpdate = bookingMapper.updateBookingStatus(booking, status);
        return bookingMapper.toBookingDto(bookingToUpdate);
    }

    public BookingDto getBookingById(Long bookingId) {
        log.info("Получен запрос в сервис на получение букинга по id");
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Букинг не найден"));
        return bookingMapper.toBookingDto(booking);
    }

    public List<BookingDto> getAllBookingsByUserId(Long userId) {
        log.info("Получен запрос в сервис на получение всех букингов пользователя");
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Такого пользователя нет"));

        return bookingMapper.toBookingDtoList(bookingRepository.findAllByUserId(userId));
    }
}
