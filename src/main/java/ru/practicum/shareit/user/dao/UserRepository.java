package ru.practicum.shareit.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
