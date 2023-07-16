package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoResp {
    private Integer id;
    private LocalDate start;
    private LocalDate end;
    private String status;
    private BookingDtoRespBooker booker;
    private BookingDtoRespItem item;
}