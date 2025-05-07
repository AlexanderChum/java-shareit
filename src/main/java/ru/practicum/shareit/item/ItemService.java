package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Item item, Long userId);

    ItemDto updateItem(Item item, Long id, Long userId);

    ItemDto getItemById(Long itemId);

    List<Item> getAllOwnerItems(Long userId);

    List<Item> getItemsBySearch(String text);
}
