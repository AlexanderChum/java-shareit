package ru.practicum.shareit.constants.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StartBeforeEndValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEnd {
    String message() default "Время окончания букинга должно быть позже времени начала";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}