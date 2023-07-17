package ru.practicum.shareit.booking.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

}