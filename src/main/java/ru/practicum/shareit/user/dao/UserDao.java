package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

public interface UserDao {
    User create(User user);
    User update(User user);
    void delete(int id);
    User find(int id);
    ArrayList<User> findAll();
}
