package ru.practicum.shareit.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.utils.AppConfiguration;

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
