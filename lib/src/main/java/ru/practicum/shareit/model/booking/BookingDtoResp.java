package ru.practicum.shareit.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoResp {
    private Integer id;

    private LocalDateTime startDate;
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