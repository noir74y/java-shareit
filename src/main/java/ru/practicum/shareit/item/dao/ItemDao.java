package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemDao {
    Item create(Item item);

    Item update(Item item);

    Item findById(int itemId);

    ArrayList<Item> findByOwner(int ownerId);

    ArrayList<Item> findByText(String text);
}
