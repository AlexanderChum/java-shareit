package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.error.exceptions.EntityConflictException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto expectedDto;
    private User testUser;
    private User duplicateEmailUser;
    private UserDto testUserDto;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        expectedDto = new UserDto(1L, "Updated Name", "updated@ya.ru");
        testUser = new User(1L, "Test User", "test@ya.ru");
        duplicateEmailUser = new User(2L, "Test User2", "test@ya.ru");
        testUserDto = new UserDto(1L, "Test User", "test@ya.ru");
        updatedUser = new User(1L, "Updated Name", "updated@ya.ru");
    }

    private UserDto createTestUpdateRequest() {
        UserDto update = new UserDto();
        update.setName("Updated Name");
        update.setEmail("updated@ya.ru");
        return update;
    }

    @Test
    void addNewUserValidDataShouldReturnUserDto() {
        when(userRepository.save(any())).thenReturn(testUser);
        when(userMapper.toUserDto(any())).thenReturn(testUserDto);

        UserDto result = userService.addNewUser(testUser);

        assertThat(result).isEqualTo(testUserDto);
        verify(userRepository).save(testUser);
    }

    @Test
    void addNewUserDuplicateEmailShouldThrowError() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        assertThrows(EntityConflictException.class,
                () -> userService.addNewUser(duplicateEmailUser));

        verify(userRepository, Mockito.never()).save(any());
    }

    @Test
    void updateUserValidDataShouldUpdateFields() {
        UserDto updateRequest = createTestUpdateRequest();

        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userMapper.updateUserFromDto(any(), any())).thenReturn(updatedUser);
        when(userMapper.toUserDto(any())).thenReturn(expectedDto);

        UserDto result = userService.updateUser(updateRequest, testUser.getId());

        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getEmail()).isEqualTo("updated@ya.ru");
        verify(userRepository).findById(testUser.getId());
    }

    @Test
    void getUserByIdExistingUserShouldReturnUserDto() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userMapper.toUserDto(any())).thenReturn(testUserDto);

        UserDto result = userService.getUserById(testUser.getId());

        assertThat(result).isEqualTo(testUserDto);
        verify(userRepository).findById(testUser.getId());
    }

    @Test
    void deleteUserExistingUserShouldRemoveFromDb() {
        userService.deleteUser(testUser.getId());
        verify(userRepository).deleteById(1L);
    }
}