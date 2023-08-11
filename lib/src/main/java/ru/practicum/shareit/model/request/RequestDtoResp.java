package ru.practicum.shareit.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.model.item.ItemDtoResp;
import ru.practicum.shareit.utils.AppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDtoResp {
    private Integer id;
    private String description;
    @DateTimeFormat(pattern = AppConfiguration.DATE_TIME_FORMAT)
    private LocalDateTime created;
    private List<ItemDtoResp> items;
}
