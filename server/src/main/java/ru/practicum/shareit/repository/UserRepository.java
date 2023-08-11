package ru.practicum.shareit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.model.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
