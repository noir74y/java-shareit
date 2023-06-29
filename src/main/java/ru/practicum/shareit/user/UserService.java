package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User create(User user) {
        if (findAll().stream().anyMatch(obj -> obj.getEmail().equals(user.getEmail())))
            throw new DuplicateEmailException(user.getEmail());

        user.setId();
        return userDao.create(user);
    }

    public User update(User user, int id) {
        user.setId(id);
        return userDao.update(user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }

    public User find(int id) {
        return userDao.find(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }
}
