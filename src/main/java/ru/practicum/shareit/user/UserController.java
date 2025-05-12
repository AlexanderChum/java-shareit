package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление нового пользователя");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.addNewUser(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user,
                                              @PathVariable String id) {
        log.info("Получен запрос на обновление пользователя");
        return ResponseEntity.ok(userService.updateUser(user, Long.parseLong(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        log.info("Получен запрос на получение пользователя по id");
        return ResponseEntity.ok(userService.getUserById(Long.parseLong(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        log.info("Получен запрос на удаление пользователя");
        userService.deleteUser(Long.parseLong(id));
    }
}
