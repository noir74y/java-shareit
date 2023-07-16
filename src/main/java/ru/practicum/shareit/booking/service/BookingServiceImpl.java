package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
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
    public Booking create(Integer requesterId, Booking booking) throws Throwable {
        if (!booking.getStart().isBefore(booking.getEnd()))
            throw new ValidationException("start is not after end", booking.getStart() + " " + booking.getEnd());

        var userEntity = Optional.of(userRepository.findById(requesterId))
                .get()
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("no such user", String.valueOf(requesterId)));

        var itemEntity = Optional.of(itemRepository.findById(booking.getItemId()))
                .get()
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("no such item", String.valueOf(booking.getItemId())));

        booking.setStatus(BookingStatus.WAITING);

        return bookingMapper.entity2model(bookingRepository.save(bookingMapper.model2entity(booking, userEntity, itemEntity)));
    }

    @Override
    @Transactional
    public Booking update(Integer requesterId, Integer bookingId, Boolean approved) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Booking findById(Integer requesterId, Integer bookingId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByBookerAndState(Integer requesterId, BookingState state) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByOwnerAndState(Integer requesterId, BookingState state) {
        return null;
    }

}
