package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private AtomicLong keyCounter = new AtomicLong(0);

    public Optional<UserDto> addNewUser(User user) {
        Long id = generateUniqueKey();
        users.put(id, user);
        log.info("Пользователь добавлен в мапу");
        return Optional.ofNullable(UserMapper.toUserDto(user, id));
    }

    public Optional<UserDto> updateUser(User user, Long id) {
        users.put(id, user);
        log.info("Пользователь обновлен в мапе");
        return Optional.ofNullable(UserMapper.toUserDto(user, id));
    }

    public Optional<UserDto> getUserById(Long id) {
        log.info("Получен запрос в репозиторий на получение пользователя");
        User user = users.get(id);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(UserMapper.toUserDto(user, id));
    }

    public void deleteUser(Long id) {
        log.info("Получен запрос на удаление пользователя");
        users.remove(id);
    }

    public Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    private Long generateUniqueKey() {
        return keyCounter.incrementAndGet();
    }
}
