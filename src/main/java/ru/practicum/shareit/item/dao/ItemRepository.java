package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity,Integer> {
}
