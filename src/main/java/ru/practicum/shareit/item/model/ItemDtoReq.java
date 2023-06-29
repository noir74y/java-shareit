package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ItemDtoReq {
    private final int id;
    private String name;
    private String description;
    private boolean available;
    private final int owner;
    private int request;
}
