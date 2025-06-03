package ru.practicum.shareit.itemAndComment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemUpdateRequest {
    private Long id;
    private User user;
    private String name;
    private String description;
    private Boolean available;
}
