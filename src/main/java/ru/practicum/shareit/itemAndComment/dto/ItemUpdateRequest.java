package ru.practicum.shareit.itemAndComment.dto;

import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 50, message = "Неверная длина имени")
    private String name;

    @Size(min = 1, max = 500, message = "Неверная длина описания")
    private String description;

    private Boolean available;
}
