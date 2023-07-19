package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class ItemServiceDbImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public Item create(Item item, int ownerId) throws Throwable {
        User user = Optional.ofNullable(this.userService.findById(ownerId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(ownerId)));
        return itemMapper.entity2model(itemRepository.save(itemMapper.model2entity(item, user)));
    }

    @Override
    @Transactional
    public Item update(Item item, int ownerId, int itemId) {
        var itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("no item with such id", String.valueOf(itemId)));

        if (!itemEntity.getOwner().getId().equals(ownerId))
            throw new ForbiddenException("это item другого юзера", itemEntity.getOwner().getId() + " != " + ownerId);

        Optional.ofNullable(item.getName()).ifPresent(itemEntity::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(itemEntity::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(itemEntity::setAvailable);

        return itemMapper.entity2model(itemRepository.save(itemEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(int itemId) {
        var item = itemMapper.entity2model(itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("no item with such id", String.valueOf(itemId))));
        item.setLastBooking(bookingRepository.getPreviousBookings(itemId, LocalDateTime.now()).stream()
                .findFirst()
                .orElse(null));
        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Item> findByOwner(int userId) {
        return itemMapper.bulkEntity2model(itemRepository.findAllByOwnerIdOrderById(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Item> findByText(String text) {
        return !text.isBlank() ? itemMapper.bulkEntity2model(itemRepository.search(text)) : new ArrayList<>();
    }
}
