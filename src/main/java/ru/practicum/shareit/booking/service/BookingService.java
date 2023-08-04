package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Integer requestorId, Booking booking) throws Throwable;

    Booking update(Integer requestorId, Integer bookingId, Boolean approved) throws Throwable;

    Booking findById(Integer requestorId, Integer bookingId) throws Throwable;

    List<Booking> findByBookerAndState(Integer requestorId, String state, Integer offset, Integer pageSize);

    List<Booking> findByOwnerAndState(Integer requestorId, String state, Integer offset, Integer pageSize);
}
