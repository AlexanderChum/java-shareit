package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Map;
import java.util.Optional;

public interface ItemStorage {

    Long addNewItem(Item item);

    Item updateItem(Item item, Long id);

    Optional<Item> getItemById(Long id);

    Map<Long, Item> getAllOwnerItems(Long userId);

    Map<Long, Item> getItemsBySearch(String text);
}
