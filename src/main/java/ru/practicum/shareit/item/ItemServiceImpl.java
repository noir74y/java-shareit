package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Item create(Item item, int ownerId) {
        checkIfOwnerExists(ownerId);
        item.setId();
        return itemDao.create(item);
    }

    @Override
    public Item update(Item item) {
        return null;
    }

    @Override
    public Item findById(int itemId) {
        return null;
    }

    @Override
    public ArrayList<Item> findByOwner(int ownerId) {
        return null;
    }

    @Override
    public ArrayList<Item> findByText(String text) {
        return null;
    }

    private void checkIfOwnerExists(int ownerId) {
        Optional.ofNullable(userService.findById(ownerId)).orElseThrow(() -> new NotFoundException("нет такого юзера", String.valueOf(ownerId)));
    }
}
