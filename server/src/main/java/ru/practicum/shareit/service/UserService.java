package ru.practicum.shareit.service;

import ru.practicum.shareit.model.user.User;

import java.util.List;

public interface UserService {
    User create(User user) throws Throwable;

    User update(User user, int userId) throws Throwable;

    void delete(int userId);

    User findById(int userId) throws Throwable;

    List<User> findAll();

}
