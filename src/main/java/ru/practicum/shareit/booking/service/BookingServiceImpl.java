package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.exception.CustomValidationException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.WrongUserException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.user.dao.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            throw new CustomValidationException("start is not after end", booking.getStart() + " " + booking.getEnd());

        var userEntity = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("no such user", String.valueOf(requesterId)));

        var itemEntity = itemRepository.findById(booking.getItemId())
                .orElseThrow(() -> new NotFoundException("no such item", String.valueOf(booking.getItemId())));

        if (requesterId.equals(itemEntity.getOwner().getId()))
            throw new NotFoundException("requester is a owner of the item", String.valueOf(requesterId));



        if (itemEntity.getAvailable()) {
            booking.setStatus(BookingStatus.WAITING);
            return bookingMapper.entity2model(bookingRepository.save(bookingMapper.model2entity(booking, userEntity, itemEntity)));
        } else
            throw new CustomValidationException("item is not available", String.valueOf(itemEntity.getId()));
    }

    @Override
    @Transactional
    public Booking update(Integer requesterId, Integer bookingId, Boolean approved) throws Throwable {
        var bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("no such bookingIg", String.valueOf(bookingId)));

        if (bookingEntity.getStatus().equals(BookingStatus.APPROVED))
            throw new CustomValidationException("booking is already approved", String.valueOf(bookingEntity.getId()));

        if (requesterId.equals(bookingEntity.getItem().getOwner().getId())) {
            bookingEntity.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
            return bookingMapper.entity2model(bookingRepository.save(bookingEntity));
        } else
            throw new NotFoundException("wrong user", String.valueOf(requesterId));
    }

    @Override
    @Transactional(readOnly = true)
    public Booking findById(Integer requesterId, Integer bookingId) throws Throwable {
        var bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("no such bookingIg", String.valueOf(bookingId)));

        if (requesterId.equals(bookingEntity.getItem().getOwner().getId()) || requesterId.equals(bookingEntity.getBooker().getId()))
            return bookingMapper.entity2model(bookingEntity);
        else
            throw new NotFoundException("user is neither booker nor owner", String.valueOf(requesterId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByBookerAndState(Integer requesterId, String state) {
        List<BookingEntity> entities = new ArrayList<>();
        var userEntity = userRepository.findById(requesterId).orElseThrow(() -> new WrongUserException(requesterId));
        switch (BookingState.valueOf(state)) {
            case FUTURE:
                entities = bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(requesterId, LocalDateTime.now());
                break;
            case CURRENT:
                entities = bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(requesterId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                entities = bookingRepository.findAllByBookerIdAndEndIsBeforeOrderByStartDesc(requesterId, LocalDateTime.now());
                break;
            case WAITING:
                entities = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(requesterId, BookingStatus.WAITING);
                break;
            case REJECTED:
                entities = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(requesterId, BookingStatus.REJECTED);
                break;
            case ALL:
                entities = bookingRepository.findAllByBookerIdOrderByStartDesc(requesterId);
        }
        return bookingMapper.bulkEntity2model(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByOwnerAndState(Integer requesterId, String state) {
        List<BookingEntity> entities = new ArrayList<>();
        var userEntity = userRepository.findById(requesterId).orElseThrow(() -> new WrongUserException(requesterId));
        switch (BookingState.valueOf(state)) {
            case FUTURE:
                entities = bookingRepository.findAllByItemOwnerIdAndStartIsAfterOrderByStartDesc(requesterId, LocalDateTime.now());
                break;
            case CURRENT:
                entities = bookingRepository.findAllByItemOwnerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(requesterId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                entities = bookingRepository.findAllByItemOwnerIdAndEndIsBeforeOrderByStartDesc(requesterId, LocalDateTime.now());
                break;
            case WAITING:
                entities = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(requesterId, BookingStatus.WAITING);
                break;
            case REJECTED:
                entities = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(requesterId, BookingStatus.REJECTED);
                break;
            case ALL:
                entities = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(requesterId);
        }
        return bookingMapper.bulkEntity2model(entities);
    }

}
