package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Item {

    @NotBlank(message = "Название для вещи должно быть задано")
    private String name;

    @NotBlank(message = "Описание для вещи должно быть задано")
    private String description;

    @NotNull(message = "Статус для вещи должен быть задан")
    private Boolean available;

    private List<String> feedbackReviews;

    private Long ownerId;
}
