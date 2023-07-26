package ru.practicum.shareit.item.repository;

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

    List<ItemEntity> findAllByRequestId(Integer requestId);

    @Query(
            value = "SELECT r.id as requestId, i.id as itemId, i.name, i.description, i.available " +
                    "FROM requests r " +
                    "JOIN items i ON i.request_id = r.id " +
                    "WHERE r.requester_id = ?1"
            , nativeQuery = true)
    List<ItemForRequestView> findAllByRequesterId(Integer requesterId);
}
