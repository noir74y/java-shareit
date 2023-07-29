package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    User create(User user);

    User update(User user);

    void delete(int userId);

    User findById(int userId);

    List<User> findAll();
}
