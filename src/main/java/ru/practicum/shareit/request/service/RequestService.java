package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;

import java.util.List;

public interface RequestService {
    RequestDtoResp create(Integer requestorId, RequestDtoReq request) throws Throwable;

    List<RequestDtoResp> findAllByUser(Integer requestorId);

    List<RequestDtoResp> findAllByOthers(Integer requestorId, Integer offset, Integer pageSIze);

    RequestDtoResp findById(Integer requestorId, Integer requestId);

//    Booking update(Integer requestorId, Integer bookingId, Boolean approved) throws Throwable;
//
//    Booking findById(Integer requestorId, Integer bookingId) throws Throwable;
//
//    List<Booking> findByBookerAndState(Integer requestorId, String state);
//
//    List<Booking> findByOwnerAndState(Integer requestorId, String state);
}
