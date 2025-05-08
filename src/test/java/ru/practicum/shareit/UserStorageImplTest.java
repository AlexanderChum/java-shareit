package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.UserStorageImpl;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserStorageImplTest {

    private UserStorageImpl storage;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        storage = new UserStorageImpl();

        user1 = new User();
        user1.setName("TestUser1");
        user1.setEmail("TestUser1@ya.ru");

        user2 = new User();
        user2.setName("TestUser2");
        user2.setEmail("TestUser2@ya.ru");
    }

    @Test
    void addNewUserShouldReturnUserId() {
        Long id = storage.addNewUser(user1);
        assertNotNull(id);
    }

    @Test
    void getUserByIdShouldReturnCorrectUserDto() {
        Long addedId = storage.addNewUser(user2);

        User found = storage.getUserById(addedId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));

        assertNotNull(found);
        assertEquals("TestUser2", found.getName());
        assertEquals("TestUser2@ya.ru", found.getEmail());
    }

    @Test
    void updateUserShouldUpdateUserFields() {
        Long addedId = storage.addNewUser(user1);

        user1.setName("TestUser1Update");
        user1.setEmail("TestUser1@yandex.ru");

        User updatedDto = storage.updateUser(user1, addedId);

        assertEquals("TestUser1Update", updatedDto.getName());
        assertEquals("TestUser1@yandex.ru", updatedDto.getEmail());
    }

    @Test
    void deleteUserShouldRemoveUser() {
        Long addedId = storage.addNewUser(user2);

        storage.deleteUser(addedId);

        assertTrue(storage.getUserById(addedId).isEmpty());
    }
}
