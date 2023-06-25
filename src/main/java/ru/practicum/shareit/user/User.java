package ru.practicum.shareit.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@RequiredArgsConstructor
public class User {
    private final int id;
    private String name;
    private String email;
}
