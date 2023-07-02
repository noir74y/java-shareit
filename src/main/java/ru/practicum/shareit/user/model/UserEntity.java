package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private static int userId = 0;
    private int id;
    private String name;
    private String email;

    private static int getNewId() {
        return ++userId;
    }

    public void setNewId() {
        this.id = UserEntity.getNewId();
    }
}
