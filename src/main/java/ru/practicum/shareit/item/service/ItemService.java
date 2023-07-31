package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.CommentDtoReq;
import ru.practicum.shareit.item.model.CommentEntity;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

public interface ItemService {
    Item create(int requestorId, Item item) throws Throwable;

    Item update(int requestorId, Item item, int itemId);

    Item findById(int requestorId, int itemId);

    ArrayList<Item> findByOwner(int requestorId);

    ArrayList<Item> findByText(int requestorId, String text);

    CommentEntity create(Integer requestorId, Integer itemId, CommentDtoReq dtoReq) throws Throwable;
}
