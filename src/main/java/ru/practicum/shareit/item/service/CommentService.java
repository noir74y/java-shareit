package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.CommentDtoReq;
import ru.practicum.shareit.item.model.CommentEntity;

public interface CommentService {
    CommentEntity create(Integer requestorId, Integer itemId, CommentDtoReq dtoReq) throws Throwable;
}
