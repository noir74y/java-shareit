package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.CustomValidationException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public Booking create(Integer userId, Booking booking) throws Throwable {
        if (!booking.getStart().isBefore(booking.getEnd()))
            throw new CustomValidationException("start is not after end", booking.getStart() + " " + booking.getEnd());

        var userEntity = Optional.of(userRepository.findById(userId))
                .get()
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("no such user", String.valueOf(userId)));

        var itemEntity = Optional.of(itemRepository.findById(booking.getItemId()))
                .get()
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("no such item", String.valueOf(booking.getItemId())));

        if (!itemEntity.getAvailable())
            throw new CustomValidationException("item is not available", String.valueOf(itemEntity.getId()));

        booking.setStatus(BookingStatus.WAITING);

        return bookingMapper.entity2model(bookingRepository.save(bookingMapper.model2entity(booking, userEntity, itemEntity)));
    }

    @Override
    @Transactional
    public Booking update(Integer userId, Integer bookingId, Boolean approved) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Booking findById(Integer userId, Integer bookingId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByBookerAndState(Integer userId, BookingState state) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByOwnerAndState(Integer userId, BookingState state) {
        return null;
    }

}
