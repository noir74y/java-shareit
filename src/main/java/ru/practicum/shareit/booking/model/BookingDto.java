package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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