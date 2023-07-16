package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoReq {
    @NotNull(groups = {OnCreate.class}, message = "itemId is absent")
    private Integer itemId;
    @NotNull(groups = {OnCreate.class}, message = "start is absent")
    private LocalDate start;
    @NotNull(groups = {OnCreate.class}, message = "end is absent")
    private LocalDate end;
}