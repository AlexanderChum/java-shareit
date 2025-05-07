package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserStorage {

    Optional<UserDto> addNewUser(User user);

    Optional<UserDto> updateUser(User user, Long id);

    Optional<UserDto> getUserById(Long id);

    void deleteUser(Long id);

    Map<Long, User> getUsers();
}
