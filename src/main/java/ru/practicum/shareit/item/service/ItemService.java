package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemService {
    Item create(Item item, int userId) throws Throwable;

    Item update(Item item, int ownerId, int itemId);

    Item findById(int itemId);

    ArrayList<Item> findByOwner(int ownerId);

    ArrayList<Item> findByText(String text);
}
