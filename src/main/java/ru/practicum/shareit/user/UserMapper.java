package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class UserMapper {
    public UserDto toUserDto(User user, Long id) {
        return new UserDto(
                id,
                user.getName(),
                user.getEmail()
        );
    }
}