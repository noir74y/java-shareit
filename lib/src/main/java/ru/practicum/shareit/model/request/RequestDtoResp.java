package ru.practicum.shareit.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private LocalDateTime created;
    private List<ItemDtoResp> items;
}
