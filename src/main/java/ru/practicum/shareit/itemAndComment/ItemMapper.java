package ru.practicum.shareit.itemAndComment;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Item;

@UtilityClass
public class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                null, //lastBooking field
                null, //nextBooking field
                item.getComments()
        );
    }

    public Item itemUpdateRequestToItem(Item item, ItemUpdateRequest itemUpdateRequest) {
        if (itemUpdateRequest.getName() != null) item.setName(itemUpdateRequest.getName());
        if (itemUpdateRequest.getDescription() != null) item.setDescription(itemUpdateRequest.getDescription());
        if (itemUpdateRequest.getAvailable() != null) item.setAvailable(itemUpdateRequest.getAvailable());
        return item;
    }
}