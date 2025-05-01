package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemDto addNewItem(Item item, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        return itemStorage.addNewItem(item);
    }

    public ItemDto updateItem(Item item, Long id, Long userId) {
        userExistence(userId);
        item.setOwnerId(userId);
        return itemStorage.updateItem(item, id);
    }

    public ItemDto getItemById(Long id) {
        return itemStorage.getItemById(id);
    }

    public List<Item> getAllOwnerItems(Long userId) {
        return itemStorage.getAllOwnerItems(userId);
    }

    public List<Item> getItemsBySearch(String text) {
        return itemStorage.getItemsBySearch(text);
    }

    private void userExistence(Long userId) {
        if (null == (userStorage.getUserById(userId))) {
            throw new EntityNotFoundException("Пользователь с таким id не найден");
        }
    }
}