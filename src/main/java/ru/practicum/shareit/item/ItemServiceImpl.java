package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return null;
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
    public ArrayList<Item> findByOwner(int userId) {
        return null;
    }

    @Override
    public ArrayList<Item> findByText(String text) {
        return null;
    }
}
