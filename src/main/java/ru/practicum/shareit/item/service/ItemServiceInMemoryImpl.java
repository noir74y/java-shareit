package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemDao;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.utils.exception.ForbiddenException;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceInMemoryImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Item create(int requestorId, Item item) throws Throwable {
        Optional.ofNullable(userService.findById(requestorId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(requestorId)));
        item.setOwnerId(requestorId);
        return itemDao.create(item);
    }

    @Override
    public Item update(int requestorId, Item item, int itemId) {
        Item obj = itemDao.findById(itemId);
        if (!obj.getOwnerId().equals(requestorId))
            throw new ForbiddenException("это item другого юзера", obj.getOwnerId() + " != " + requestorId);
        Optional.ofNullable(item.getName()).ifPresent(obj::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(obj::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(obj::setAvailable);
        return itemDao.update(obj);
    }

    @Override
    public Item findById(int requestorId, int itemId) {
        return itemDao.findById(itemId);
    }

    @Override
    public ArrayList<Item> findByOwner(int requestorId) {
        return itemDao.findByOwner(requestorId);
    }

    @Override
    public ArrayList<Item> findByText(int requestorId, String text) {
        return itemDao.findByText(text);
    }
}
