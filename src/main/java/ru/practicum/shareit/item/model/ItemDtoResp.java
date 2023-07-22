package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoResp {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private ItemBooking lastBooking;
    private ItemBooking nextBooking;
    private List<CommentDtoResp> comments;
}
