package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Item {
    private static int itemId = 0;
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private int owner;
    private int request;

    private static int getNewId() {
        return ++itemId;
    }

    public void setNewId() {
        this.id = Item.getNewId();
    }
}
