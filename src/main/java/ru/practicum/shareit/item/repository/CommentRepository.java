package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
