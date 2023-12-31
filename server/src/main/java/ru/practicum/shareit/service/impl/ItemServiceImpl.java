package ru.practicum.shareit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.mapper.CommentMapper;
import ru.practicum.shareit.mapper.ItemMapper;
import ru.practicum.shareit.model.comment.CommentDtoReq;
import ru.practicum.shareit.model.comment.CommentEntity;
import ru.practicum.shareit.model.item.Item;
import ru.practicum.shareit.model.item.ItemBooking;
import ru.practicum.shareit.model.item.ItemEntity;
import ru.practicum.shareit.repository.BookingRepository;
import ru.practicum.shareit.repository.CommentRepository;
import ru.practicum.shareit.repository.ItemRepository;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.ItemService;
import ru.practicum.shareit.service.UserService;
import ru.practicum.shareit.utils.error.exception.CustomValidationException;
import ru.practicum.shareit.utils.error.exception.ForbiddenException;
import ru.practicum.shareit.utils.error.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public Item create(int requestorId, Item item) throws Throwable {
        var userEntity = userRepository.findById(requestorId)
                .orElseThrow(() -> new NotFoundException("no such user", String.valueOf(requestorId)));
        return itemMapper.entity2model(itemRepository.save(itemMapper.model2entity(item, userEntity)));
    }

    @Override
    @Transactional
    public CommentEntity create(Integer requestorId, Integer itemId, CommentDtoReq dtoReq) throws Throwable {
        var userEntity = userRepository.findById(requestorId)
                .orElseThrow(() -> new NotFoundException("no such user", String.valueOf(requestorId)));

        var itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("no such item", String.valueOf(itemId)));

        if (!commentRepository.isCommentFairy(requestorId, itemId, LocalDateTime.now()))
            throw new CustomValidationException("Comment won't be fairy", "sorry");

        return commentRepository.save(commentMapper.dtoReq2entity(dtoReq, userEntity, itemEntity));
    }

    @Override
    @Transactional
    public Item update(int requestorId, Item item, int itemId) {
        var itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("no such item", String.valueOf(itemId)));

        if (!itemEntity.getOwner().getId().equals(requestorId))
            throw new ForbiddenException("это item другого юзера", itemEntity.getOwner().getId() + " != " + requestorId);

        Optional.ofNullable(item.getName()).ifPresent(itemEntity::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(itemEntity::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(itemEntity::setAvailable);

        return itemMapper.entity2model(itemRepository.save(itemEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(int requestorId, int itemId) {
        return setLastNextBookingsAndCommentsAndMapToItem(itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("no item with such id", String.valueOf(itemId))), requestorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByOwner(int requestorId) {
        return itemRepository.findAllByOwnerIdOrderById(requestorId).stream()
                .map(itemEntity -> setLastNextBookingsAndCommentsAndMapToItem(itemEntity, requestorId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findByText(int requestorId, String text) {
        return itemMapper.bulkEntity2model(itemRepository.search(text));
    }

    private Item setLastNextBookingsAndCommentsAndMapToItem(ItemEntity itemEntity, Integer requestorId) {
        var item = itemMapper.entity2model(itemEntity);

        item.setLastBooking(Optional.ofNullable(bookingRepository.getLastBooking(requestorId, item.getId()))
                .map(bookingEntity -> ItemBooking.builder()
                        .id(bookingEntity.getId())
                        .bookerId(bookingEntity.getBooker().getId())
                        .build())
                .orElse(null));

        item.setNextBooking(Optional.ofNullable(bookingRepository.getNextBooking(requestorId, item.getId()))
                .map(bookingEntity -> ItemBooking.builder()
                        .id(bookingEntity.getId())
                        .bookerId(bookingEntity.getBooker().getId())
                        .build())
                .orElse(null));

        item.setComments(
                commentRepository.findCommentsOfOtherUsers(item.getId())
                        .stream()
                        .map(commentMapper::entity2dtoResp)
                        .collect(Collectors.toList()));

        return item;
    }
}
