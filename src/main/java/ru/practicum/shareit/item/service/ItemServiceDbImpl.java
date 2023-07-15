package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Primary
public class ItemServiceDbImpl implements ItemService {
    private final ItemDao itemDao;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;

    @Override
    @Transactional
    public Item create(Item item, int ownerId) throws Throwable {
        User user = Optional.ofNullable(userService.findById(ownerId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(ownerId)));
        return itemMapper.entity2item(itemRepository.save(itemMapper.item2entity(item, user)));
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

        return itemMapper.entity2item(itemRepository.save(itemEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(int itemId) {
        return itemMapper.entity2item(itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("no item with such id", String.valueOf(itemId))));
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Item> findByOwner(int ownerId) {
        return itemDao.findByOwner(ownerId);
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Item> findByText(String text) {
        return itemDao.findByText(text);
    }
}
