package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static ru.practicum.shareit.utils.AppConstants.DATE_TIME_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoResp {
    private Integer id;

    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime endDate;

    private String status;
    private BookingDtoRespBooker booker;
    private BookingDtoRespItem item;

    @JsonProperty("start")
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    @JsonProperty("end")
    public LocalDateTime getEndDate() {
        return this.endDate;
    }
}