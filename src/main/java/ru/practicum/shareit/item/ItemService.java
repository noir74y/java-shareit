package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemService {
    Item create(Item item, int userId);

    Item update(Item item);

    Item findById(int itemId);

    ArrayList<Item> findByOwner(int ownerId);

    ArrayList<Item> findByText(String text);
}
