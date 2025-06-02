package ru.practicum.shareit.user;

import org.springframework.transaction.annotation.Transactional;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto addNewUser(User user) {
        uniqueEmailCheck(user.getEmail());
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(UserDto userDto, Long id) {
        if (null != (userDto.getEmail())) uniqueEmailCheck(userDto.getEmail());
        userDto.setId(id);
        log.info("Пройдена проверка на уникальность email, отправка запроса в репозиторий");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        User userToUpdate = userMapper.updateUserFromDto(userDto, user);

        return userMapper.toUserDto(userToUpdate);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        log.info("Сделан запрос в сервис на получение пользователя по userId");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не был найден"));
        return userMapper.toUserDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Сделан запрос на удаление пользователя");
        userRepository.deleteById(id);
    }

    private void uniqueEmailCheck(String email) {
        boolean emailExists = userRepository.findAll().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
        if (emailExists) {
            throw new EntityConflictException("Email '" + email + "' уже используется");
        }
    }
}
