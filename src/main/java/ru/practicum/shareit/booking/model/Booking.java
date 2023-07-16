package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Booking {
    private Integer id;
    private LocalDate start;
    private LocalDate end;
    private Integer itemId;
    private Integer bookerId;
    private BookingStatus status;
}
