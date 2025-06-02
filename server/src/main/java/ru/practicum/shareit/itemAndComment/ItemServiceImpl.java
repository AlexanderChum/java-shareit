package ru.practicum.shareit.itemAndComment;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.constants.BookingStatus;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.error.exceptions.ValidationException;
import ru.practicum.shareit.itemAndComment.dto.CommentDto;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemNewRequest;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;
import ru.practicum.shareit.itemAndComment.model.Item;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;
    private final ItemRequestRepository itemRequestRepository;

    @Transactional
    public ItemDto addNewItem(ItemNewRequest itemNewRequest, Long userId) {
        Item item = itemMapper.toItem(itemNewRequest);
        item.setUser(userExistence(userId));
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        if (itemNewRequest.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemNewRequest.getRequestId())
                    .orElseThrow(() -> new EntityNotFoundException("Запроса на данный предмет не существует"));
            item.setRequest(itemRequest);
        }
        return itemMapper.toItemDto(itemRepository.save(item));
    }

    @Transactional
    public ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, Long id, Long userId) {
        itemUpdateRequest.setUser(userExistence(userId));

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не найден"));
        Item itemToUpdate = itemMapper.updateItemFromRequest(itemUpdateRequest, item);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return itemMapper.toItemDto(itemToUpdate);
    }

    @Transactional(readOnly = true)
    public ItemDto getItemById(Long id) {
        log.info("Поступил запрос в сервис на получение предмета");
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));
        return itemMapper.toItemDto(item);
    }

    @Transactional(readOnly = true)
    public List<ItemDto> getAllOwnerItems(Long userId) {
        log.info("Поступил запрос в сервис на получение всех предметов пользователя");
        return itemMapper.toItemDtoList(itemRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<ItemDto> getItemsBySearch(String text) {
        log.info("Поступил запрос в сервис на получение всех предметов по поиску");
        if (text.isBlank()) return List.of();
        return itemMapper.toItemDtoList(itemRepository.findItemsByTextSearch(text));
    }

    @Transactional
    public CommentDto addNewComment(Comment comment, Long itemId, Long userId) {
        log.info("Поступил запрос на добавление нового комментария");
        User user = userExistence(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));

        if (bookingRepository.findLastBookingEndByItemId(itemId).isAfter(LocalDateTime.now())) {
            throw new ValidationException("Букинг не завершен");
        }

        if (!bookingRepository.existsByBookerIdAndItemIdAndStatus(userId, itemId, BookingStatus.APPROVED)) {
            throw new ValidationException("Пользователь не букировал данный предмет");
        }

        comment.setItem(item);
        comment.setUser(user);
        comment.setAuthorName(user.getName());

        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    private User userExistence(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }
}