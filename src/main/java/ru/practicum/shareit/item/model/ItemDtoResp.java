package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoResp {
    private Integer id;
    private String name;
    private String description;
    private ItemBooking lastBooking;
    private ItemBooking nextBooking;
    private Boolean available;
}
