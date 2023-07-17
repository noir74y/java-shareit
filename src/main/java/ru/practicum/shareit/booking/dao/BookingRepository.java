package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.BookingEntity;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    List<BookingEntity> findByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime);

    List<BookingEntity> findByBookerIdAndEndBeforeOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime);

    List<BookingEntity> findByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer bookerId, LocalDateTime currentDateTime1, LocalDateTime currentDateTime2);

    List<BookingEntity> findByBookerIdAndStatusOrderByStartDesc(Integer bookerId, BookingStatus status);

    List<BookingEntity> findByBookerIdOrderByStartDesc(Integer bookerId);
}