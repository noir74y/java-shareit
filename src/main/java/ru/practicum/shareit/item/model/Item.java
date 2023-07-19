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
    private ItemBooking lastBooking;
    private ItemBooking nextBooking;
    private Boolean available;
    private Integer ownerId;
}
