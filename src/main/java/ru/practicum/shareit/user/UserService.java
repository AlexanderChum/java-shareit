package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityConflictException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public UserDto addNewUser(User user) {
        uniqueEmailCheck(user.getEmail());
        return userStorage.addNewUser(user);
    }

    public UserDto updateUser(User user, Long id) {
        if (null != (user.getEmail())) uniqueEmailCheck(user.getEmail());
        return userStorage.updateUser(user, id);
    }

    public UserDto getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }

    private void uniqueEmailCheck(String email) {
        for (User user : userStorage.getUsers().values()) {
            if (email.equals(user.getEmail())) {
                throw new EntityConflictException("Такой email уже используется, используйте другой");
            }
        }
    }
}
