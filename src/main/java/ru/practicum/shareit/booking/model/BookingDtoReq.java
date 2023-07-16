package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.shareit.AppConstants.DATE_TIME_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoReq {
    @NotNull
    private Integer itemId;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @Future
    private LocalDateTime end;
}