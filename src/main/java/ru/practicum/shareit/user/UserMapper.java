package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user, Long id) {
        return new UserDto(
                id,
                user.getName(),
                user.getEmail()
        );
    }
}