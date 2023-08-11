package ru.practicum.shareit.service;

import ru.practicum.shareit.model.comment.CommentDtoReq;
import ru.practicum.shareit.model.comment.CommentEntity;
import ru.practicum.shareit.model.item.Item;

import java.util.List;

public interface ItemService {
    Item create(int requestorId, Item item) throws Throwable;

    Item update(int requestorId, Item item, int itemId);

    Item findById(int requestorId, int itemId);

    List<Item> findByOwner(int requestorId);

    List<Item> findByText(int requestorId, String text);

    CommentEntity create(Integer requestorId, Integer itemId, CommentDtoReq dtoReq) throws Throwable;
}
