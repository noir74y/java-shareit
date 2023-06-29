package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User create(User user) {
        checkEmailForDuplicate(user.getEmail());
        user.setId();
        return userDao.create(user);
    }

    public User update(User user, int id) {
        checkEmailForDuplicate(user.getEmail());
        User userInStorage = find(id);
        user.setId(id);
        Optional.ofNullable(user.getName()).ifPresent(userInStorage::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(userInStorage::setEmail);
        return userInStorage;
    }

    public void delete(int id) {
        userDao.delete(id);
    }

    public User find(int id) {
        return userDao.find(id);
    }

    public ArrayList<User> findAll() {
        return userDao.findAll();
    }

    private void checkEmailForDuplicate(String email) {
        if (findAll().stream().anyMatch(obj -> obj.getEmail().equals(email)))
            throw new DuplicateEmailException(email);
    }
}
