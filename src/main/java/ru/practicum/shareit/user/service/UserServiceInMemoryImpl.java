package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserDao;
import ru.practicum.shareit.utils.exception.DuplicateEmailException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceInMemoryImpl implements ru.practicum.shareit.user.service.UserService {
    private final UserDao userDao;

    @Override
    public User create(User user) {
        checkEmailForDuplicate(user);
        return userDao.create(user);
    }

    @Override
    public User update(User user, int userId) {
        user.setId(userId);
        checkEmailForDuplicate(user);
        User obj = findById(userId);
        Optional.ofNullable(user.getName()).ifPresent(obj::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(obj::setEmail);
        return userDao.update(obj);
    }

    @Override
    public void delete(int userId) {
        userDao.delete(userId);
    }

    @Override
    public User findById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    private void checkEmailForDuplicate(User user) {
        if (findAll().stream().anyMatch(obj -> !obj.getId().equals(user.getId()) && obj.getEmail().equals(user.getEmail())))
            throw new DuplicateEmailException(user.getEmail());
    }
}
