package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Booking {
    private Integer id;
    private Integer itemId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String itemName;
    private Integer bookerId;
    private BookingStatus status;
}
