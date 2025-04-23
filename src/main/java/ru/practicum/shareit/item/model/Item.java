package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Data
@RequiredArgsConstructor
public class Item {

    private Long itemId;

    @NotNull(message = "Название для вещи должно быть задано")
    private String name;

    private String description;

    @NotNull(message = "Статус для вещи должен задан")
    private String status;

    private List<String> feedbackReviews;

    @NotNull(message = "У владельца вещи должен быть id")
    @Positive(message = "Id владельца вещи должен быть положительный")
    private Long ownerId;
}
