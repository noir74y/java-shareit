package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
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
@Primary
public class ItemServiceInMemoryImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Item create(Item item, int ownerId) throws Throwable {
        Optional.ofNullable(userService.findById(ownerId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(ownerId)));
        item.setOwnerId(ownerId);
        return itemDao.create(item);
    }

    @Override
    public Item update(Item item, int ownerId, int itemId) {
        Item obj = itemDao.findById(itemId);
        if (!obj.getOwnerId().equals(ownerId))
            throw new ForbiddenException("это item другого юзера", obj.getOwnerId() + " != " + ownerId);
        Optional.ofNullable(item.getName()).ifPresent(obj::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(obj::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(obj::setAvailable);
        return itemDao.update(obj);
    }

    @Override
    public Item findById(int itemId) {
        return itemDao.findById(itemId);
    }

    @Override
    public ArrayList<Item> findByOwner(int ownerId) {
        return itemDao.findByOwner(ownerId);
    }

    @Override
    public ArrayList<Item> findByText(String text) {
        return itemDao.findByText(text);
    }
}
