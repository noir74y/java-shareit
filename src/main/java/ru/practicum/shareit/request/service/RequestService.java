package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;

import java.util.List;

public interface RequestService {
    RequestDtoResp create(Integer requesterId, RequestDtoReq request) throws Throwable;

    List<RequestDtoResp> findAllByUser(Integer requesterId);

    List<RequestDtoResp> findAllByOthers(Integer requesterId, Integer offset, Integer pageSIze);

    RequestDtoResp findById(Integer requesterId, Integer requestId);

//    Booking update(Integer requesterId, Integer bookingId, Boolean approved) throws Throwable;
//
//    Booking findById(Integer requesterId, Integer bookingId) throws Throwable;
//
//    List<Booking> findByBookerAndState(Integer requesterId, String state);
//
//    List<Booking> findByOwnerAndState(Integer requesterId, String state);
}
