package ru.practicum.shareit.itemAndComment;

import org.mapstruct.*;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Item;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemMapper {

    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    ItemDto toItemDto(Item item);

    Item updateItemFromRequest(ItemUpdateRequest request, @MappingTarget Item item);

    List<ItemDto> toItemDtoList(List<Item> items);
}