package ru.practicum.shareit.booking.repository;

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

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date < ?3 " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date desc " +
            "limit 1", nativeQuery = true)
    BookingEntity getLastBooking(Integer requester_id, Integer itemId, LocalDateTime localDateTime);

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date > ?3 " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date asc " +
            "limit 1", nativeQuery = true)
    BookingEntity getNextBooking(Integer requester_id,Integer itemId, LocalDateTime localDateTime);
}