package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
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

    public Long addNewUser(User user) {
        Long id = generateUniqueKey();
        users.put(id, user);
        log.info("Пользователь добавлен в мапу");
        return id;
    }

    public User updateUser(User user, Long id) {
        users.put(id, user);
        log.info("Пользователь обновлен в мапе");
        return user;
    }

    public Optional<User> getUserById(Long id) {
        log.info("Получен запрос в репозиторий на получение пользователя");
        return Optional.ofNullable(users.get(id));
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
