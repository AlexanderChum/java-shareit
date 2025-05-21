package ru.practicum.shareit.itemAndComment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingStorage;
import ru.practicum.shareit.constants.BookingStatus;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;

    public Item addNewItem(Item item, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return itemStorage.save(item);
    }

    public Item updateItem(ItemUpdateRequest itemUpdateRequest, Long id, Long userId) {
        userExistence(userId);
        itemUpdateRequest.setOwnerId(userId);

        Item item = itemStorage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не найден"));
        Item itemToUpdate = ItemMapper.itemUpdateRequestToItem(item, itemUpdateRequest);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return itemStorage.save(itemToUpdate);
    }

    public Item getItemById(Long id) {
        log.info("Поступил запрос в сервис на получение предмета");
        return itemStorage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));
    }

    public List<ItemDto> getAllOwnerItems(Long userId) {
        log.info("Поступил запрос в сервис на получение всех предметов пользователя");
        return convertItemListToDtoList(itemStorage.findByOwnerId(userId));
    }

    public List<ItemDto> getItemsBySearch(String text) {
        log.info("Поступил запрос в сервис на получение всех предметов по поиску");
        if (text.isBlank()) return List.of();
        return convertItemListToDtoList(itemStorage.findItemsByTextSearch(text));
    }

    public Comment addNewComment(Comment comment, Long itemId, Long userId) {
        log.info("Поступил запрос на добавление нового комментария");
        User user = userExistence(userId);
        Item item = getItemById(itemId);

        if (bookingStorage.findLastBookingEndByItemId(itemId).isAfter(LocalDateTime.now())) {
            throw new ValidationException("Букинг не завершен");
        }

        if (!bookingStorage.existsByBookerIdAndItemIdAndStatus(userId, itemId, BookingStatus.APPROVED)) {
            throw new ValidationException("Пользователь не букировал данный предмет");
        }

        comment.setItem(item);
        comment.setUser(user);
        comment.setAuthorName(user.getName());

        return commentStorage.save(comment);
    }

    private List<ItemDto> convertItemListToDtoList(List<Item> items) {
        return items.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private User userExistence(Long userId) {
        return userService.getUserById(userId);
    }
}