package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceInMemoryImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Item create(int requesterId, Item item) throws Throwable {
        Optional.ofNullable(userService.findById(requesterId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(requesterId)));
        item.setOwnerId(requesterId);
        return itemDao.create(item);
    }

    @Override
    public Item update(int requesterId, Item item, int itemId) {
        Item obj = itemDao.findById(itemId);
        if (!obj.getOwnerId().equals(requesterId))
            throw new ForbiddenException("это item другого юзера", obj.getOwnerId() + " != " + requesterId);
        Optional.ofNullable(item.getName()).ifPresent(obj::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(obj::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(obj::setAvailable);
        return itemDao.update(obj);
    }

    @Override
    public Item findById(int requesterId, int itemId) {
        return itemDao.findById(itemId);
    }

    @Override
    public ArrayList<Item> findByOwner(int requesterId) {
        return itemDao.findByOwner(requesterId);
    }

    @Override
    public ArrayList<Item> findByText(int requesterId, String text) {
        return itemDao.findByText(text);
    }
}
