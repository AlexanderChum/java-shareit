package ru.practicum.shareit.itemAndComment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    @Size(min = 1, max = 500, message = "Неверная длина имени")
    private String text;
}
