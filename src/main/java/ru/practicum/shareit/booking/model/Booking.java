package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Booking {
    private Integer id;
    private Integer itemId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String itemName;
    private Integer bookerId;
    private BookingStatus status;
}
