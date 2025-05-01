package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private AtomicLong keyCounter = new AtomicLong(0);

    public UserDto addNewUser(User user) {
        Long id = generateUniqueKey();
        users.put(id, user);
        return new UserDto(id, user.getName(), user.getEmail());
    }

    public UserDto updateUser(User user, Long id) {
        users.put(id, user);
        return new UserDto(id, user.getName(), user.getEmail());
    }

    public UserDto getUserById(Long id) {
        if (users.containsKey(id)) {
            return new UserDto(id, users.get(id).getName(), users.get(id).getEmail());
        } else return null;
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    public Map<Long, User> getUsers() {
        return new HashMap<>(users);
    }

    private Long generateUniqueKey() {
        return keyCounter.incrementAndGet();
    }
}
