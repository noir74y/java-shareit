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
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User create(User user) {
        checkEmailForDuplicate(user);
        user.setId();
        return userDao.create(user);
    }

    @Override
    public User update(User user, int id) {
        user.setId(id);
        checkEmailForDuplicate(user);
        User obj = find(id);
        Optional.ofNullable(user.getName()).ifPresent(obj::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(obj::setEmail);
        return userDao.update(obj);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User find(int id) {
        return userDao.find(id);
    }

    @Override
    public ArrayList<User> findAll() {
        return userDao.findAll();
    }

    private void checkEmailForDuplicate(User user) {
        if (findAll().stream().anyMatch(obj -> obj.getId() != user.getId() && obj.getEmail().equals(user.getEmail())))
            throw new DuplicateEmailException(user.getEmail());
    }
}
