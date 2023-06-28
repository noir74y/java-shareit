package ru.practicum.shareit.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
public class UserEntity {
    private static int userId = 0;
    private int id;
    private String name;
    private String email;

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setId() {
        this.id = ++userId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
