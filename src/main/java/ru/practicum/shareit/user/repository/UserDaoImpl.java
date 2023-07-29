package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final HashMap<Integer, UserEntity> userEntities;
    private final UserMapper userMapper;

    @Override
    public User create(User user) {
        UserEntity userEntity = userMapper.model2entity(user);
        userEntity.setNewId();
        userEntities.put(userEntity.getId(), userEntity);
        return userMapper.entity2model(userEntity);
    }

    @Override
    public User update(User user) {
        userEntities.replace(user.getId(), userMapper.model2entity(user));
        return user;
    }

    @Override
    public void delete(int userId) {
        userEntities.remove(userId);
    }

    @Override
    public User findById(int userId) {
        return userMapper.entity2model(userEntities.get(userId));
    }

    @Override
    public List<User> findAll() {
        return userMapper.bulkEntity2model(userEntities.values());
    }
}
