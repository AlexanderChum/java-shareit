package ru.practicum.shareit.itemAndComment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.practicum.shareit.itemAndComment.dto.CommentRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemRequestDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequestDto;

import static ru.practicum.shareit.constants.Const.REQUEST_HEADER_ID;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addNewItem(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                             @RequestHeader(name = REQUEST_HEADER_ID) long userId) {
        log.info("Поступил запрос на добавление предмета");
        return itemClient.addNewItem(itemRequestDto, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@Valid @RequestBody ItemUpdateRequestDto itemUpdateRequestDto,
                                             @PathVariable @Positive long id,
                                             @RequestHeader(name = REQUEST_HEADER_ID) long userId) {
        log.info("Поступил запрос на обновление предмета");
        return itemClient.updateItem(itemUpdateRequestDto, userId, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable @Positive long id) {
        log.info("Поступил запрос на получение предмета по id");
        return itemClient.getItemById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllOwnerItems(@RequestHeader(name = REQUEST_HEADER_ID) long userId) {
        log.info("Поступил запрос на получение всех предметов пользователя");
        return itemClient.getAllOwnerItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getAllBySearch(@RequestParam String text) {
        log.info("Поступил запрос на поиск предмета по поиску");
        return itemClient.getAllBySearch(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addNewComment(@Valid @PathVariable("itemId") long itemId,
                                                @RequestHeader(name = REQUEST_HEADER_ID) long userId,
                                                @RequestBody @Valid CommentRequestDto commentRequestDto) {
        return itemClient.addNewComment(commentRequestDto, userId, itemId);
    }
}
