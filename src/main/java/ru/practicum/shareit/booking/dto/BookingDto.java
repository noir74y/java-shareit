package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
@RequiredArgsConstructor
public class BookingDto {
    private final int id;
    private final LocalDate start;
    private final LocalDate end;
    private final int item;
    private final int booker;
    private String status;
}