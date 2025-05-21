package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.constants.validators.StartBeforeEnd;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@StartBeforeEnd
public class BookingRequest {

    @Positive
    @NotNull
    private final Long itemId;

    @NotNull
    private final LocalDateTime start;

    @NotNull
    @Future
    private final LocalDateTime end;
}
