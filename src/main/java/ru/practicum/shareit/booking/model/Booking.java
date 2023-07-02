package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Booking {
    private int id;
    private LocalDate start;
    private LocalDate end;
    private int item;
    private int booker;
    private BookingStatus status;
}
