package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserStorage {

    Long addNewUser(User user);

    User updateUser(User user, Long id);

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

    Map<Long, User> getUsers();
}
