package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private static Integer userId = 0;
    private Integer id;
    private String name;
    private String email;

    private static Integer getNewId() {
        return ++userId;
    }

    public void setNewId() {
        this.id = UserEntity.getNewId();
    }
}
