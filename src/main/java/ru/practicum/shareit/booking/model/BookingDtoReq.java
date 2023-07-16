package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @NotNull(groups = {OnCreate.class}, message = "itemId is absent")
    private Integer itemId;

    @NotNull(groups = {OnCreate.class}, message = "start is absent")
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime start;

    @NotNull(groups = {OnCreate.class}, message = "end is absent")
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    @Future
    private LocalDateTime end;
}