package ru.practicum.shareit.service;

import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.model.request.RequestDtoResp;

import java.util.List;

public interface RequestService {
    RequestDtoResp create(Integer requestorId, RequestDtoReq request) throws Throwable;

    List<RequestDtoResp> findAllByUser(Integer requestorId);

    List<RequestDtoResp> findAllByOthers(Integer requestorId, Integer offset, Integer pageSize);

    RequestDtoResp findById(Integer requestorId, Integer requestId);
}
