package ru.practicum.shareit.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UserDaoInMemoryImpl implements UserDao {
    private final HashMap<Integer, UserEntity> userEntities;
    private final UserMapper userMapper;

    @Override
    public User create(User user) {
        userEntities.put(user.getId(), userMapper.user2entity(user));
        return user;
    }

    @Override
    public User update(User user) {
        userEntities.replace(user.getId(), userMapper.user2entity(user));
        return user;
    }

    @Override
    public void delete(int id) {
        userEntities.remove(id);
    }

    @Override
    public User find(int id) {
        return userMapper.entity2user(userEntities.get(id));
    }

    @Override
    public ArrayList<User> findAll() {
        return userMapper.bulkEntity2user(userEntities.values());
    }
}
