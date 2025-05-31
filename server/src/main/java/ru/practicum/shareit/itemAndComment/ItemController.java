package ru.practicum.shareit.itemAndComment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
import ru.practicum.shareit.itemAndComment.dto.CommentDto;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemNewRequest;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;

import java.util.List;

import static ru.practicum.shareit.constants.Constants.REQUEST_HEADER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addNewItem(@Valid @RequestBody ItemNewRequest itemNewRequest,
                                              @RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Поступил запрос на добавление предмета");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.addNewItem(itemNewRequest, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest,
                                              @PathVariable @Positive Long id,
                                              @RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Поступил запрос на обновление предмета");
        return ResponseEntity.ok(itemService.updateItem(itemUpdateRequest, id, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable @Positive Long id) {
        log.info("Поступил запрос на получение предмета по id");
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllOwnerItems(@RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Поступил запрос на получение всех предметов пользователя");
        return ResponseEntity.ok(itemService.getAllOwnerItems(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> getAllBySearch(@RequestParam String text) {
        log.info("Поступил запрос на поиск предмета по поиску");
        return ResponseEntity.ok(itemService.getItemsBySearch(text));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addNewComment(@Valid @PathVariable("itemId") Long itemId,
                                                    @RequestHeader(name = REQUEST_HEADER_ID) Long userId,
                                                    @RequestBody @Valid Comment comment) {
        return ResponseEntity.ok(itemService.addNewComment(comment, itemId, userId));
    }
}
