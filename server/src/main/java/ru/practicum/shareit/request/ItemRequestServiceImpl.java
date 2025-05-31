package ru.practicum.shareit.request;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRequestMapper itemRequestMapper;

    @Transactional
    public ItemRequestDto addNewItemRequest(ItemRequest itemRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Такой пользователь не найден"));
        itemRequest.setRequester(user);
        log.info("Пройдена проверка для добавления нового ItemRequest");
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ItemRequestDto getRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Запрос не был найден"));
        log.info("Пройдена проверка для получения Request по id");
        return itemRequestMapper.toItemRequestDto(itemRequest);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ItemRequestDto> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Такой пользователь не был найден"));
        log.info("Пройдена проверка для получения Requests по id");
        return itemRequestMapper.toListDto(itemRequestRepository.findByRequesterId(userId));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<ItemRequestDto> getAllRequests() {
        log.info("Получен запрос в сервис на получение всех Requests");
        return itemRequestMapper.toListDto(itemRequestRepository.findAllByOrderByCreatedDesc());
    }
}
