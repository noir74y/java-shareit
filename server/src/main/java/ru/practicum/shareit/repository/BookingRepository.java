package ru.practicum.shareit.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.model.booking.BookingEntity;
import ru.practicum.shareit.model.booking.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    List<BookingEntity> findAllByBookerIdAndStartDateIsAfterOrderByStartDateDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndStartDateIsAfterOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateAsc(Integer bookerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByItemOwnerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<BookingEntity> findAllByBookerIdAndEndDateIsBeforeOrderByStartDateDesc(Integer bookerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndEndDateIsBeforeOrderByStartDateDesc(Integer ownerId, LocalDateTime localDateTime);

    List<BookingEntity> findAllByBookerIdAndStatusOrderByStartDateDesc(Integer bookerId, BookingStatus status);

    List<BookingEntity> findAllByItemOwnerIdAndStatusOrderByStartDateDesc(Integer ownerId, BookingStatus status);

    List<BookingEntity> findAllByBookerIdOrderByStartDateDesc(Integer bookerId, Pageable page);

    List<BookingEntity> findAllByItemOwnerIdOrderByStartDateDesc(Integer ownerId, Pageable page);

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date < CURRENT_TIMESTAMP(6) " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date desc " +
            "limit 1", nativeQuery = true)
    BookingEntity getLastBooking(Integer requestorId, Integer itemId);

    @Query(value = "select b.* from bookings b " +
            "join items i on i.id = b.item_id and i.owner_id = ?1 " +
            "where b.item_id = ?2 " +
            "and b.start_date > CURRENT_TIMESTAMP(6) " +
            "and b.status = 'APPROVED' " +
            "order by b.start_date asc " +
            "limit 1", nativeQuery = true)
    BookingEntity getNextBooking(Integer requestorId, Integer itemId);
}