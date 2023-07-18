package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.BookingEntity;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    List<BookingEntity> findAllByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Integer ownerId, LocalDateTime currentDateTime);

    List<BookingEntity> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime1, LocalDateTime currentDateTime2);

    List<BookingEntity> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer ownerId, LocalDateTime currentDateTime1, LocalDateTime currentDateTime2);

    List<BookingEntity> findAllByBookerIdAndEndBeforeOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime);

    List<BookingEntity> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Integer ownerId, LocalDateTime currentDateTime);

    List<BookingEntity> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, BookingStatus status);

    List<BookingEntity> findAllByItemOwnerIdAndStatusOrderByStartDesc(Integer ownerId, BookingStatus status);

    List<BookingEntity> findAllByBookerIdOrderByStartDesc(Integer bookerId);

    List<BookingEntity> findAllByItemOwnerIdOrderByStartDesc(Integer ownerId);
}