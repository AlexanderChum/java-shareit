package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequest {
    private Long id;

    @Size(min = 1, max = 50, message = "Неверная длина имени")
    private String name;

    @Email
    private String email;
}
