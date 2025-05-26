package ru.practicum.shareit.itemAndComment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime created;
    private String authorName;

}
