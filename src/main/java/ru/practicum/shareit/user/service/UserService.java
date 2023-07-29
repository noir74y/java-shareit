package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User create(User user) throws Throwable;

    User update(User user, int userId) throws Throwable;

    void delete(int userId);

    User findById(int userId) throws Throwable;

    List<User> findAll();

}
