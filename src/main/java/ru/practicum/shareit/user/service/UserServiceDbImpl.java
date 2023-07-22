package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class UserServiceDbImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public User create(User user) {
        return userMapper.entity2model(userRepository.save(userMapper.model2entity(user)));
    }

    @Override
    @Transactional
    public User update(User user, int userId) throws Throwable {
        user.setId(userId);
        User obj = findById(userId);
        Optional.ofNullable(user.getName()).ifPresent(obj::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(obj::setEmail);
        return userMapper.entity2model(userRepository.save(userMapper.model2entity(obj)));
    }

    @Override
    @Transactional
    public void delete(int userId) {
        userRepository.deleteAllById(List.of(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int userId) throws Throwable {
        return userMapper.entity2model(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("no user with such id", String.valueOf(userId))));
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<User> findAll() {
        return userMapper.bulkEntity2model(userRepository.findAll());
    }
}
