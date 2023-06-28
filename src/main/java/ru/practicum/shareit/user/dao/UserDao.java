package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserDao {
    User create(UserEntity userEntity);
    User update(UserEntity userEntity);
    void delete(int id);
    User find(int id);
    List<User> findAll();
}
