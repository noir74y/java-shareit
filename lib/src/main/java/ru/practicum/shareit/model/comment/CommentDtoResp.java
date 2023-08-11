package ru.practicum.shareit.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoResp {
    private Integer id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
