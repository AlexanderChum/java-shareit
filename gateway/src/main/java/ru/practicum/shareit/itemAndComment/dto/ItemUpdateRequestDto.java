package ru.practicum.shareit.itemAndComment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.dto.UserRequestDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateRequestDto {

    private Long id;
    private UserRequestDto userRequestDto;

    @Size(min = 1, max = 50, message = "Неверная длина имени")
    private String name;

    @Size(min = 1, max = 500, message = "Неверная длина описания")
    private String description;

    private Boolean available;
}
