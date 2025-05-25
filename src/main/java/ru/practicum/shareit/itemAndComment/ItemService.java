package ru.practicum.shareit.itemAndComment;

import ru.practicum.shareit.itemAndComment.dto.CommentDto;
import ru.practicum.shareit.itemAndComment.dto.ItemDto;
import ru.practicum.shareit.itemAndComment.dto.ItemUpdateRequest;
import ru.practicum.shareit.itemAndComment.model.Comment;
import ru.practicum.shareit.itemAndComment.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(Item item, Long userId);

    ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, Long id, Long userId);

    ItemDto getItemById(Long itemId);

    List<ItemDto> getAllOwnerItems(Long userId);

    List<ItemDto> getItemsBySearch(String text);

    CommentDto addNewComment(Comment comment, Long itemId, Long userId);
}
