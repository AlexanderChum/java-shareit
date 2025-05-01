package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Map;

public interface UserStorage {

    UserDto addNewUser(User user);

    UserDto updateUser(User user, Long id);

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    Map<Long, User> getUsers();
}
