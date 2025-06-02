package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

import static ru.practicum.shareit.constants.Constants.REQUEST_HEADER_ID;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ResponseEntity<ItemRequestDto> addNewItemRequest(@RequestHeader(name = REQUEST_HEADER_ID) Long userId,
                                                            @RequestBody ItemRequest itemRequest) {
        log.info("Получен запрос на добавление нового ItemRequest");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemRequestService.addNewItemRequest(itemRequest, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> getUserRequests(@RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Получен запрос на получение всех Request пользователя");
        return ResponseEntity.ok(itemRequestService.getUserRequests(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllItemRequests() {
        log.info("Получен запрос на получение всех Request");
        return ResponseEntity.ok(itemRequestService.getAllRequests());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getRequestById(@PathVariable Long requestId) {
        log.info("Получен запрос на получение Request по id");
        return ResponseEntity.ok(itemRequestService.getRequestById(requestId));
    }
}
