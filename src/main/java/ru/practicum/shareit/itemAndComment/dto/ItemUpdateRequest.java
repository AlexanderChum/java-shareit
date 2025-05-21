package ru.practicum.shareit.itemAndComment.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ItemUpdateRequest {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private Boolean available;
}
