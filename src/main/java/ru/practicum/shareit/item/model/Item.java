package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime lastBooking;
    private InternalError lastBookerId;
    private LocalDateTime nextBooking;
    private InternalError nextBookerId;
    private Boolean available;
    private Integer ownerId;
}
