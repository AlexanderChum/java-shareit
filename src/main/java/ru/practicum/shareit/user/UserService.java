package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;

public interface UserService {
    User addNewUser(User user);

    User updateUser(UserUpdateRequest userUpdateRequest, Long userId);

    User getUserById(Long userId);

    void deleteUser(Long userId);
}
