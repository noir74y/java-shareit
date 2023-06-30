package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ItemEntity {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private int owner;
    private int request;
}
