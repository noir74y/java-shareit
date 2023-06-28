package ru.practicum.shareit.user.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoInMemoryImpl implements UserDao {
    private final HashMap<Integer, UserEntity> userEntities;
    private final UserMapper userMapper;

    @Override
    public User create(UserEntity userEntity) {
        userEntities.put(userEntity.getId(), userEntity);
        return userMapper.entity2user(userMapper.user2entity(find(userEntity.getId())));
    }

    @Override
    public User update(UserEntity userEntity) {
        userEntities.replace(userEntity.getId(), userEntity);
        return userMapper.entity2user(userMapper.user2entity(find(userEntity.getId())));
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
    public List<User> findAll() {
        return userMapper.bulkEntity2user(new ArrayList<>(userEntities.values()));
    }
}
