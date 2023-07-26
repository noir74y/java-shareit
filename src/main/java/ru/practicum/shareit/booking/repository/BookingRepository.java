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

    List<BookingEntity> findAllByBookerIdAndStartDateIsAfterOrderByStartDateDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndStartDateIsAfterOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateDesc(Integer bookerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByItemOwnerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByBookerIdAndEndDateIsBeforeOrderByStartDateDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndEndDateIsBeforeOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStatusOrderByStartDateDesc(Integer bookerId, BookingStatus status);

    List<BookingEntity> findAllByItemOwnerIdAndStatusOrderByStartDateDesc(Integer ownerId, BookingStatus status);

    List<BookingEntity> findAllByBookerIdOrderByStartDateDesc(Integer bookerId);

    List<BookingEntity> findAllByItemOwnerIdOrderByStartDateDesc(Integer ownerId);

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date < CURRENT_TIMESTAMP() " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date desc " +
            "limit 1", nativeQuery = true)
    BookingEntity getLastBooking(Integer requestorId, Integer itemId);

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date > CURRENT_TIMESTAMP() " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date asc " +
            "limit 1", nativeQuery = true)
    BookingEntity getNextBooking(Integer requestorId, Integer itemId);
}