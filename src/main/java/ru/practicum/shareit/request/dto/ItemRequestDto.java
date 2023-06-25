package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@RequiredArgsConstructor
public class ItemRequestDto {
    private final int id;
    private String description;
    private final int requester;
    private final LocalDate created;
}
