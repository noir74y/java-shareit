package ru.practicum.shareit.request.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ItemRequest {
    private int id;
    private String description;
    private int requester;
    private LocalDate created;
}
