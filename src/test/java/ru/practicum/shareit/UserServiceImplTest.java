package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.exceptions.EntityConflictException;
import ru.practicum.shareit.error.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "ru.practicum.shareit")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@ya.ru");
    }

    @Test
    void addNewUserValidDataShouldReturnUserDto() {
        UserDto savedUser = userService.addNewUser(testUser);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Test User");
        assertThat(savedUser.getEmail()).isEqualTo("test@ya.ru");
    }

    @Test
    void addNewUserDuplicateEmailShouldThrowError() {
        userService.addNewUser(testUser);
        User duplicateUser = new User();
        duplicateUser.setName("Test User2");
        duplicateUser.setEmail("test@ya.ru");

        assertThrows(EntityConflictException.class, () -> userService.addNewUser(duplicateUser));
    }

    @Test
    void updateUserValidDataShouldUpdateFields() {
        UserDto savedUser = userService.addNewUser(testUser);
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setName("Updated Name");
        updateRequest.setEmail("updated@ya.ru");

        UserDto updatedUser = userService.updateUser(updateRequest, savedUser.getId());

        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@ya.ru");
    }

    @Test
    void updateUserNonExistingIdShouldThrowNotFound() {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setName("Updated Name");

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(updateRequest, 999L));
    }

    @Test
    void getUserByIdExistingUserShouldReturnUserDto() {
        UserDto savedUser = userService.addNewUser(testUser);
        UserDto foundUser = userService.getUserById(savedUser.getId());

        assertThat(foundUser).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    void deleteUserExistingUserShouldRemoveFromDb() {
        UserDto savedUser = userService.addNewUser(testUser);
        userService.deleteUser(savedUser.getId());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(savedUser.getId()));
    }
}