package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {
    Booking create(Integer requesterId, Booking booking);

    Booking update(Integer requesterId, Integer bookingId, Boolean approved);

    Booking findById(Integer requesterId, Integer bookingId);

    List<Booking> findByBookerAndState(Integer requesterId, State state);

    List<Booking> findByOwnerAndState(Integer requesterId, State state);
}
