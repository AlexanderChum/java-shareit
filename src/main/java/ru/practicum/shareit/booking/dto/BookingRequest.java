package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.constants.validators.StartBeforeEnd;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@StartBeforeEnd
public class BookingRequest {

    @Positive
    @NotNull
    private Long itemId;

    @NotNull
    private LocalDateTime start;

    @NotNull
    @Future
    private LocalDateTime end;
}
