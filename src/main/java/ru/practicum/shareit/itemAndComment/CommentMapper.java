package ru.practicum.shareit.itemAndComment;

import org.mapstruct.Mapper;
import ru.practicum.shareit.itemAndComment.dto.CommentDto;
import ru.practicum.shareit.itemAndComment.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);
}