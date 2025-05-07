package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {

    Optional<ItemDto> addNewItem(Item item);

    Optional<ItemDto> updateItem(Item item, Long id);

    Optional<ItemDto> getItemById(Long id);

    List<Item> getAllOwnerItems(Long userId);

    List<Item> getItemsBySearch(String text);
}
