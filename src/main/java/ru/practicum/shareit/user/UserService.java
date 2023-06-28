package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    public UserDto create(User user) {
        user.setId();
        return userMapper.user2dto(userDao.create(userMapper.user2entity(user)));
    }

    public UserDto update(User user, int id) {
        user.setId(id);
        return userMapper.user2dto(userDao.update(userMapper.user2entity(user)));
    }

    public void delete(int id) {
        userDao.delete(id);
    }

    public UserDto find(int id) {
        return userMapper.user2dto(userDao.find(id));
    }

    public List<UserDto> findAll() {
        return userMapper.bulkUser2dto(userDao.findAll());
    }
}
