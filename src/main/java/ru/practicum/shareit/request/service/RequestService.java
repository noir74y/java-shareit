package ru.practicum.shareit.request.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.model.RequestDtoReq;

import java.util.List;

public interface RequestService {
    Request create(Integer requesterId, Request request) throws Throwable;

//    Booking update(Integer requesterId, Integer bookingId, Boolean approved) throws Throwable;
//
//    Booking findById(Integer requesterId, Integer bookingId) throws Throwable;
//
//    List<Booking> findByBookerAndState(Integer requesterId, String state);
//
//    List<Booking> findByOwnerAndState(Integer requesterId, String state);
}
