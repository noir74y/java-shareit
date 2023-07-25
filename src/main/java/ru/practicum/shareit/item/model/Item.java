package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer requestId;
    private ItemBooking lastBooking;
    private ItemBooking nextBooking;
    private List<CommentDtoResp> comments;
    private Integer ownerId;
}
