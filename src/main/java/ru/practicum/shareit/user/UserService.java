package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

public interface UserService {
    User create(User user);

    User update(User user, int userId);

    void delete(int userId);

    User findById(int userId);

    ArrayList<User> findAll();

}
