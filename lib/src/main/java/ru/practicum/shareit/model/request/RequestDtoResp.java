package ru.practicum.shareit.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.model.item.ItemDtoResp;

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
