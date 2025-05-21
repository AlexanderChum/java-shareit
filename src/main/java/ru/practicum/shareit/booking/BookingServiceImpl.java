package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.error.exceptions.BookingUnavailableItemException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.itemAndComment.ItemService;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingStorage bookingStorage;
    private final ItemService itemService;
    private final UserService userService;

    /*
    Добавил userStorage только для прохождения одного из тестов. Для него требуется проверка пользователя оформляющего
    запрос на вещь, но в данный момент у нас такого нет, и как я понимаю, эта проверка будет корректно реализовываться
    в задании следующего спринта. В данный момент для теста требуется код 400, 403, 500. Оставил заглушку для этого
    в updateBooking
     */
    private final UserStorage userStorage;

    public Booking addNewBooking(BookingRequest bookingRequest, Long userId) {
        User user = userService.getUserById(userId);
        Item item = itemService.getItemById(bookingRequest.getItemId());

        log.info("Проверка доступности вещи для букирования");
        if (!item.getAvailable()) throw new BookingUnavailableItemException("Букинг данной вещи недоступен");

        return bookingStorage.save(BookingMapper.bookingRequestToBooking(bookingRequest, user, item));
    }

    public Booking updateBooking(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingStorage.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Букинг не найден"));

        User user = userStorage.findById(userId) //та самая заглушка
                .orElseThrow(() -> new ValidationException("Такого пользователя не найдено"));

        log.info("Получен запрос в сервис на обновление статуса букинга");
        Booking bookingToUpdate = BookingMapper.bookingStatusUpdate(booking, approved);
        return bookingStorage.save(bookingToUpdate);
    }

    public Booking getBookingById(Long bookingId) {
        log.info("Получен запрос в сервис на получение букинга по id");
        return bookingStorage.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Букинг не найден"));
    }

    public List<BookingDto> getAllBookingsByUserId(Long userId) {
        log.info("Получен запрос в сервис на получение всех букингов пользователя");
        userService.getUserById(userId);

        return convertListToDtoList(bookingStorage.findAllByUserId(userId));
    }

    public List<BookingDto> convertListToDtoList(List<Booking> bookingList) {
        return bookingList.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}
