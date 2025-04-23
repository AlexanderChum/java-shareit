package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;

/**
 * TODO Sprint add-controllers.
 */
public class User {

    private Long userId;

    private String name;

    @Email
    private String email;
}
