package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityConflictException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public UserDto addNewUser(User user) {
        uniqueEmailCheck(user.getEmail());
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");
        return userStorage.addNewUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был создан"));
    }

    public UserDto updateUser(User user, Long id) {
        if (null != (user.getEmail())) uniqueEmailCheck(user.getEmail());
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");
        return userStorage.updateUser(user, id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был обновлен"));
    }

    public UserDto getUserById(Long id) {
        log.info("Сделан запрос в сервис на получение пользователя по userId");
        return userStorage.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));
    }

    public void deleteUser(Long id) {
        log.info("Сделан запрос на удаление пользователя");
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
