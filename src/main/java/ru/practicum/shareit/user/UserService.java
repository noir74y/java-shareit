package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

public interface UserService {
    User create(User user);

    User update(User user, int id);

    void delete(int id);

    User find(int id);

    ArrayList<User> findAll();
}
