package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ItemRequestMapper {

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest);

    List<ItemRequestDto> toListDto(List<ItemRequest> itemRequests);
}
