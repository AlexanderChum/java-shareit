package ru.practicum.shareit.itemAndComment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.itemAndComment.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
    private List<Comment> comments;
}
