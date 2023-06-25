package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
@RequiredArgsConstructor
public class Booking {
    private final int id;
    private final LocalDate start;
    private final LocalDate end;
    private final int item;
    private final int booker;
    private BookingStatus status;
}
