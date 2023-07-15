package ru.practicum.shareit.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Query("select ie from ItemEntity ie " +
            "where (upper(ie.name) like upper(concat('%', ?1, '%')) " +
            "or upper(ie.description) like upper(concat('%', ?1, '%')))" +
            "and ie.available = true")
    List<ItemEntity> search(String text);

    List<ItemEntity> findAllByOwnerIdOrderById(Integer userId);
}
