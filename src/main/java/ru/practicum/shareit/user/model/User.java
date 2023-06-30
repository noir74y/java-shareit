package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private static int userId = 0;
    private int id;
    private String name;
    private String email;

    public void setNewId() {
        this.id = ++userId;
    }
}
