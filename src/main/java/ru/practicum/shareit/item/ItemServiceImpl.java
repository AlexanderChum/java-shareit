package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto addNewItem(Item item, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return itemStorage.addNewItem(item)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был создан"));
    }

    public ItemDto updateItem(Item item, Long id, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        log.info("Проведена проверка на существование пользователя и присвоен userId предмету");
        return itemStorage.updateItem(item, id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был обновлен"));
    }

    public ItemDto getItemById(Long id) {
        log.info("Поступил запрос в сервис на получение предмета");
        return itemStorage.getItemById(id)
                .orElseThrow(() -> new EntityNotFoundException("Предмет не был найден"));
    }

    public List<Item> getAllOwnerItems(Long userId) {
        log.info("Поступил запрос в сервис на получение всех предметов пользователя");
        return itemStorage.getAllOwnerItems(userId);
    }

    public List<Item> getItemsBySearch(String text) {
        log.info("Поступил запрос в сервис на получение всех предметов по поиску");
        return itemStorage.getItemsBySearch(text);
    }

    private void userExistence(Long userId) {
        if (null == (userStorage.getUserById(userId))) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
    }
}