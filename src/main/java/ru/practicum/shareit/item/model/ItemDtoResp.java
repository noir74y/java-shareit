package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoResp {
    private Integer id;
    private String name;
    private String description;
    private ItemDtoRespLastBooking lastBooking;
    private ItemDtoRespNextBooking nextBooking;
    private Boolean available;
}
