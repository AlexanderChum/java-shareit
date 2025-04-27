package ru.practicum.shareit.user;

import java.util.Map;

public interface UserStorage {

    User addNewUser(User user);

    User updateUser(User user, Long id);

    User getUserById(Long id);

    void deleteUser(Long id);

    Map<Long, User> getUsers();
}
