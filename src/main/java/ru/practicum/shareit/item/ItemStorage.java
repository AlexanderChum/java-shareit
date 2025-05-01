package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    ItemDto addNewItem(Item item);

    ItemDto updateItem(Item item, Long id);

    ItemDto getItemById(Long id);

    List<Item> getAllOwnerItems(Long userId);

    List<Item> getItemsBySearch(String text);
}
