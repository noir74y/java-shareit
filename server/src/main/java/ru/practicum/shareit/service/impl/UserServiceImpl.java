package ru.practicum.shareit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.mapper.UserMapper;
import ru.practicum.shareit.model.user.User;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.UserService;
import ru.practicum.shareit.utils.error.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
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
    public List<User> findAll() {
        return userMapper.bulkEntity2model(userRepository.findAll());
    }
}
