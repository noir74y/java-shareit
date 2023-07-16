package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface BookingService {
    Booking create(Integer userId, Booking booking) throws Throwable;

    Booking update(Integer userId, Integer bookingId, Boolean approved);

    Booking findById(Integer userId, Integer bookingId);

    List<Booking> findByBookerAndState(Integer userId, BookingState state);

    List<Booking> findByOwnerAndState(Integer userId, BookingState state);
}
