package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.BookingEntity;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    List<BookingEntity> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Integer bookerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Integer ownerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, BookingStatus status);

    List<BookingEntity> findAllByItemOwnerIdAndStatusOrderByStartDesc(Integer ownerId, BookingStatus status);

    List<BookingEntity> findAllByBookerIdOrderByStartDesc(Integer bookerId);

    List<BookingEntity> findAllByItemOwnerIdOrderByStartDesc(Integer ownerId);

    @Query("select b.start from BookingEntity as b where b.item.id = ?1 and b.start > ?2 order by b.start desc")
    List<LocalDateTime> getPreviousBookings(Integer itemId, LocalDateTime localDateTime);
}