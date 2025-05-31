package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;

public interface UserService {
    UserDto addNewUser(User user);

    UserDto updateUser(UserUpdateRequest userUpdateRequest, Long userId);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);
}
