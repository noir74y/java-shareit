package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique=true)
    private String email;

    private Integer request;

    private static Integer userId = 0;

    private static Integer getNewId() {
        return ++userId;
    }

    public void setNewId() {
        this.id = UserEntity.getNewId();
    }
}
