package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemService {
    Item create(int requesterId, Item item) throws Throwable;

    Item update(int requesterId, Item item, int itemId);

    Item findById(int requesterId, int itemId);

    ArrayList<Item> findByOwner(int requesterId);

    ArrayList<Item> findByText(int requesterId, String text);
}
