package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    public ItemDto addNewItem(Item item, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return ItemMapper.toItemDto(item, itemStorage.addNewItem(item));
    }

    public ItemDto updateItem(Item item, Long id, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return ItemMapper.toItemDto(itemStorage.updateItem(item, id), id);
    }

    public ItemDto getItemById(Long id) {
        log.info("Поступил запрос в сервис на получение предмета");
        Item item = itemStorage.getItemById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));
        return ItemMapper.toItemDto(item, id);
    }

    public List<ItemDto> getAllOwnerItems(Long userId) {
        log.info("Поступил запрос в сервис на получение всех предметов пользователя");
        return convertMapToDtoList(itemStorage.getAllOwnerItems(userId));
    }

    public List<ItemDto> getItemsBySearch(String text) {
        log.info("Поступил запрос в сервис на получение всех предметов по поиску");
        return convertMapToDtoList(itemStorage.getItemsBySearch(text));
    }

    private List<ItemDto> convertMapToDtoList(Map<Long, Item> items) {
        return items.entrySet().stream()
                .map(entry -> ItemMapper.toItemDto(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    private void userExistence(Long userId) {
        userService.getUserById(userId);
    }
}