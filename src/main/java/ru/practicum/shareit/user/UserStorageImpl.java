package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private AtomicLong keyCounter = new AtomicLong(0);

    public User addNewUser(User user) {
        users.put(generateUniqueKey(), user);
        return user;
    }

    public User updateUser(User user, Long id) {
        users.put(id, user);
        return user;
    }

    public User getUserById(Long id) {
        return users.get(id);
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
