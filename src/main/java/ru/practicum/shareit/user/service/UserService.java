package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

public interface UserService {
    User create(User user) throws Throwable;

    User update(User user, int userId) throws Throwable;

    void delete(int userId);

    User findById(int userId) throws Throwable;

    ArrayList<User> findAll();

}
