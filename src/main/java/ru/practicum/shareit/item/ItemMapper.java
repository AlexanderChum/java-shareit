package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
public class ItemMapper {
    public ItemDto toItemDto(Item item, Long id) {
        return new ItemDto(
                id,
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }
}