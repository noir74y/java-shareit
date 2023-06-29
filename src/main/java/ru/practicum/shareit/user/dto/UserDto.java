package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private static int userId = 0;
    private int id;
    private String name;
    private String email;

    public UserDto(String name, String email) {
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
