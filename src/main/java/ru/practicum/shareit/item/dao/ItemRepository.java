package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity,Integer> {
}
