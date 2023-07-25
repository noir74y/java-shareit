package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestEntity;

import java.util.List;

public interface RequestService {
    RequestEntity create(Integer requesterId, RequestDtoReq request) throws Throwable;

    List<RequestEntity> findByUser(Integer requesterId);

//    Booking update(Integer requesterId, Integer bookingId, Boolean approved) throws Throwable;
//
//    Booking findById(Integer requesterId, Integer bookingId) throws Throwable;
//
//    List<Booking> findByBookerAndState(Integer requesterId, String state);
//
//    List<Booking> findByOwnerAndState(Integer requesterId, String state);
}
