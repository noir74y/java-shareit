package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@RequiredArgsConstructor
public class ItemDto {
    private final int id;
    private String name;
    private String description;
    private boolean available;
    private final int owner;
    private int request;
}
