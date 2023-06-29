package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ItemRequest {
    private final int id;
    private String description;
    private final int requester;
    private final LocalDate created;
}
