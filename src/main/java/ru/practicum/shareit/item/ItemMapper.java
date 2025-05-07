package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto(Item item, Long id) {
        return new ItemDto(
                id,
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }
}