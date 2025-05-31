package ru.practicum.shareit.itemAndComment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    @NotBlank(message = "Название для вещи должно быть задано")
    private String name;

    @NotBlank(message = "Описание для вещи должно быть задано")
    private String description;

    private Boolean available;
    private Long requestId;
}