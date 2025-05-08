package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "в качестве email указана не почта")
    @NotBlank(message = "email не может быть пустым")
    private String email;
}
