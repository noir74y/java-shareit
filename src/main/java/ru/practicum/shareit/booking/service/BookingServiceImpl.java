package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.utils.exception.CustomValidationException;
import ru.practicum.shareit.utils.exception.NotFoundException;
import ru.practicum.shareit.utils.exception.WrongUserException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public Booking create(Integer requestorId, Booking booking) throws Throwable {
        if (!booking.getStartDate().isBefore(booking.getEndDate()))
            throw new CustomValidationException("start is not before end", booking.getStartDate() + " " + booking.getEndDate());

        var userEntity = userRepository.findById(requestorId)
                .orElseThrow(() -> new NotFoundException("no such user", String.valueOf(requestorId)));

        var itemEntity = itemRepository.findById(booking.getItemId())
                .orElseThrow(() -> new NotFoundException("no such item", String.valueOf(booking.getItemId())));

        if (requestorId.equals(itemEntity.getOwner().getId()))
            throw new NotFoundException("requester is a owner of the item", String.valueOf(requestorId));

        if (itemEntity.getAvailable()) {
            booking.setStatus(BookingStatus.WAITING);
            return bookingMapper.entity2model(bookingRepository.save(bookingMapper.model2entity(booking, userEntity, itemEntity)));
        } else
            throw new CustomValidationException("item is not available", String.valueOf(itemEntity.getId()));
    }

    @Override
    @Transactional
    public Booking update(Integer requestorId, Integer bookingId, Boolean approved) throws Throwable {
        var bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("no such bookingIg", String.valueOf(bookingId)));

        if (bookingEntity.getStatus().equals(BookingStatus.APPROVED))
            throw new CustomValidationException("booking is already approved", String.valueOf(bookingEntity.getId()));

        if (requestorId.equals(bookingEntity.getItem().getOwner().getId())) {
            bookingEntity.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
            return bookingMapper.entity2model(bookingRepository.save(bookingEntity));
        } else
            throw new NotFoundException("wrong user", String.valueOf(requestorId));
    }

    @Override
    @Transactional(readOnly = true)
    public Booking findById(Integer requestorId, Integer bookingId) throws Throwable {
        var bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("no such bookingIg", String.valueOf(bookingId)));

        if (requestorId.equals(bookingEntity.getItem().getOwner().getId()) || requestorId.equals(bookingEntity.getBooker().getId()))
            return bookingMapper.entity2model(bookingEntity);
        else
            throw new NotFoundException("user is neither booker nor owner", String.valueOf(requestorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByBookerAndState(Integer requestorId, String state) {
        List<BookingEntity> entities = new LinkedList<>();
        var userEntity = userRepository.findById(requestorId).orElseThrow(() -> new WrongUserException(requestorId));
        switch (BookingState.valueOf(state)) {
            case FUTURE:
                entities = bookingRepository.findAllByBookerIdAndStartDateIsAfterOrderByStartDateDesc(requestorId, LocalDateTime.now());
                break;
            case CURRENT:
                entities = bookingRepository.findAllByBookerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateDesc(requestorId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                entities = bookingRepository.findAllByBookerIdAndEndDateIsBeforeOrderByStartDateDesc(requestorId, LocalDateTime.now());
                break;
            case WAITING:
                entities = bookingRepository.findAllByBookerIdAndStatusOrderByStartDateDesc(requestorId, BookingStatus.WAITING);
                break;
            case REJECTED:
                entities = bookingRepository.findAllByBookerIdAndStatusOrderByStartDateDesc(requestorId, BookingStatus.REJECTED);
                break;
            case ALL:
                entities = bookingRepository.findAllByBookerIdOrderByStartDateDesc(requestorId);
        }
        return bookingMapper.bulkEntity2model(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByOwnerAndState(Integer requestorId, String state) {
        List<BookingEntity> entities = new LinkedList<>();
        var userEntity = userRepository.findById(requestorId).orElseThrow(() -> new WrongUserException(requestorId));
        switch (BookingState.valueOf(state)) {
            case FUTURE:
                entities = bookingRepository.findAllByItemOwnerIdAndStartDateIsAfterOrderByStartDateDesc(requestorId, LocalDateTime.now());
                break;
            case CURRENT:
                entities = bookingRepository.findAllByItemOwnerIdAndStartDateIsBeforeAndEndDateIsAfterOrderByStartDateDesc(requestorId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                entities = bookingRepository.findAllByItemOwnerIdAndEndDateIsBeforeOrderByStartDateDesc(requestorId, LocalDateTime.now());
                break;
            case WAITING:
                entities = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDateDesc(requestorId, BookingStatus.WAITING);
                break;
            case REJECTED:
                entities = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDateDesc(requestorId, BookingStatus.REJECTED);
                break;
            case ALL:
                entities = bookingRepository.findAllByItemOwnerIdOrderByStartDateDesc(requestorId);
        }
        return bookingMapper.bulkEntity2model(entities);
    }

}
