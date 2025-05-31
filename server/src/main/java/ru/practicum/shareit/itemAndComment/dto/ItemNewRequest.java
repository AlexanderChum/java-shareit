package ru.practicum.shareit.itemAndComment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemNewRequest {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}
