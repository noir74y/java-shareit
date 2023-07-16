package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import static ru.practicum.shareit.AppConstants.DATE_TIME_FORMAT;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoResp {
    private Integer id;

    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime start;

    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime end;

    private String status;
    private BookingDtoRespBooker booker;
    private BookingDtoRespItem item;
}