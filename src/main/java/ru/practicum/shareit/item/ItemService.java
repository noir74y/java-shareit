package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemService {
    Item create(Item item);

    Item update(Item item);

    Item findById(int itemId);

    ArrayList<Item> findByOwner(int userId);

    ArrayList<Item> findByText(String text);
}
