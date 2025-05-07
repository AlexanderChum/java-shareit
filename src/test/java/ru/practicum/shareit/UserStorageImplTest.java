package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.UserStorageImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;

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
    void addNewUserShouldReturnUserDtoWithIdAndCorrectFields() {
        UserDto dto = storage.addNewUser(user1)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был создан"));

        assertNotNull(dto.getId());
        assertEquals("TestUser1", dto.getName());
        assertEquals("TestUser1@ya.ru", dto.getEmail());
    }

    @Test
    void getUserByIdShouldReturnCorrectUserDto() {
        UserDto added = storage.addNewUser(user2)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был создан"));

        UserDto found = storage.getUserById(added.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));

        assertNotNull(found);
        assertEquals(added.getId(), found.getId());
        assertEquals("TestUser2", found.getName());
        assertEquals("TestUser2@ya.ru", found.getEmail());
    }

    @Test
    void updateUserShouldUpdateUserFields() {
        UserDto added = storage.addNewUser(user1)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был создан"));

        user1.setName("TestUser1Update");
        user1.setEmail("TestUser1@yandex.ru");

        UserDto updatedDto = storage.updateUser(user1, added.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был обновлен"));

        assertEquals(added.getId(), updatedDto.getId());
        assertEquals("TestUser1Update", updatedDto.getName());
        assertEquals("TestUser1@yandex.ru", updatedDto.getEmail());
    }

    @Test
    void deleteUserShouldRemoveUser() {
        UserDto added = storage.addNewUser(user2)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был создан"));

        storage.deleteUser(added.getId());

        assertNull(storage.getUserById(added.getId()));
    }
}
