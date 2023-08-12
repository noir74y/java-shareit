package ru.practicum.shareit.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.utils.AppConfiguration;
import ru.practicum.shareit.utils.validation.BookingDatesConstraint;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@BookingDatesConstraint
public class BookingDtoReq {
    @NotNull
    private Integer itemId;

    @DateTimeFormat(pattern = AppConfiguration.DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = AppConfiguration.DATE_TIME_FORMAT)
    private LocalDateTime endDate;

    @JsonProperty("start")
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}