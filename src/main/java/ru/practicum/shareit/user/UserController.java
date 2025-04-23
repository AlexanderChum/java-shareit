package ru.practicum.shareit.user;

import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user) {

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @PathVariable @Positive Long id) {

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable @Positive Long id) {

        return ResponseEntity.ok(new User());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Positive Long id) {

    }
}
