package ru.practicum.shareit.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.model.comment.CommentDtoResp;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
