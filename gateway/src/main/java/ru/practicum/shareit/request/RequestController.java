package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
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

import static ru.practicum.shareit.constants.Const.REQUEST_HEADER_ID;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addNewItemRequest(@RequestHeader(name = REQUEST_HEADER_ID) Long userId,
                                                    @RequestBody ItemRequestDto itemRequest) {
        log.info("Получен запрос на добавление нового ItemRequest");
        return requestClient.addNewItemRequest(itemRequest, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(name = REQUEST_HEADER_ID) Long userId) {
        log.info("Получен запрос на получение всех Request пользователя");
        return requestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequests() {
        log.info("Получен запрос на получение всех Request");
        return requestClient.getAllItemsRequests();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable @Positive Long requestId) {
        log.info("Получен запрос на получение Request по id");
        return requestClient.getRequestById(requestId);
    }
}
