package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    @Override
    public Booking create(Integer requesterId, Booking booking) {
        return null;
    }

    @Override
    public Booking update(Integer requesterId, Integer bookingId, Boolean approved) {
        return null;
    }

    @Override
    public Booking findById(Integer requesterId, Integer bookingId) {
        return null;
    }

    @Override
    public List<Booking> findByBookerAndState(Integer requesterId, BookingState state) {
        return null;
    }

    @Override
    public List<Booking> findByOwnerAndState(Integer requesterId, BookingState state) {
        return null;
    }

}
