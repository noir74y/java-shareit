package ru.practicum.shareit.request.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.RequestEntity;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    List<RequestEntity> findAllByRequesterIdOrderByCreatedDesc(Integer requesterId);

    Page<RequestEntity> findAllByRequesterIdNotOrderByCreatedDesc(Integer requesterId, Pageable page);


}