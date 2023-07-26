package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestEntity;

import java.util.List;

public interface RequestService {
    RequestDtoResp create(Integer requesterId, RequestDtoReq request) throws Throwable;

    List<RequestDtoResp> findByUser(Integer requesterId);

//    Booking update(Integer requesterId, Integer bookingId, Boolean approved) throws Throwable;
//
//    Booking findById(Integer requesterId, Integer bookingId) throws Throwable;
//
//    List<Booking> findByBookerAndState(Integer requesterId, String state);
//
//    List<Booking> findByOwnerAndState(Integer requesterId, String state);
}
