package ru.practicum.shareit.item.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ItemDaoInMemoryImpl implements ItemDao {
    private final HashMap<Integer, ItemEntity> itemEntities;
    private final ItemMapper itemMapper;
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
