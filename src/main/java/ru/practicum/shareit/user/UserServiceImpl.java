package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exceptions.EntityConflictException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    public User addNewUser(User user) {
        uniqueEmailCheck(user.getEmail());
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");
        return userStorage.save(user);
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, Long id) {
        if (null != (userUpdateRequest.getEmail())) uniqueEmailCheck(userUpdateRequest.getEmail());
        userUpdateRequest.setId(id);
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");
        return userStorage.save(UserMapper.userUpdateRequestToUser(userUpdateRequest, userStorage.findById(id).get()));
    }

    public User getUserById(Long id) {
        log.info("Сделан запрос в сервис на получение пользователя по userId");
        return userStorage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));
    }

    public void deleteUser(Long id) {
        log.info("Сделан запрос на удаление пользователя");
        userStorage.deleteById(id);
    }

    private void uniqueEmailCheck(String email) {
        boolean emailExists = userStorage.findAll().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
        if (emailExists) {
            throw new EntityConflictException("Email '" + email + "' уже используется");
        }
    }
}
