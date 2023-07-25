package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Request {
    private Integer id;
    private String description;
    private LocalDateTime created;
}
