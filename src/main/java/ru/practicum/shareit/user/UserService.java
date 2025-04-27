package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.ValidationException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addNewUser(User user) {
        uniqueEmailCheck(user.getEmail());
        return userStorage.addNewUser(user);
    }

    public User updateUser(User user, Long id) {
        User userToUpdate = userStorage.getUserById(id);

        return userStorage.updateUser(user, id);
    }

    public User getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public void deleteUser(Long id) {
        userStorage.deleteUser(id);
    }

    private void uniqueEmailCheck(String email) {
        for (User user : userStorage.getUsers().values()) {
            if (email.equals(user.getEmail())) {
                throw new ValidationException("Такой email уже используется, используйте другой");
            }
        }
    }
}
