package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query("select case when count(b)>0 then true else false end " +
            "from BookingEntity b " +
            "where booker.id = ?1 " +
            "and item.id = ?2 " +
            "and endDate <= ?3 " +
            "and status = 'APPROVED'")
    boolean isCommentFairy(Integer requestorId, Integer itemId, LocalDateTime currentDateTime);

    @Query("select c " +
            "from CommentEntity c " +
            "where item.id = ?1 " +
            "order by created asc")
    List<CommentEntity> findCommentsOfOtherUsers(Integer itemId);
}
