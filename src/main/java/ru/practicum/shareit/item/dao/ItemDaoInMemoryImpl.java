package ru.practicum.shareit.item.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ItemDaoInMemoryImpl implements ItemDao {
    private final HashMap<Integer, ItemEntity> itemEntities;
    private final ItemMapper itemMapper;

    @Override
    public Item create(Item item) {
        itemEntities.put(item.getId(), itemMapper.item2entity(item));
        return item;
    }

    @Override
    public Item update(Item item) {
        itemEntities.replace(item.getId(), itemMapper.item2entity(item));
        return item;
    }

    @Override
    public Item findById(int itemId) {
        return itemMapper.entity2item(itemEntities.get(itemId));
    }

    @Override
    public ArrayList<Item> findByOwner(int ownerId) {
        return itemMapper.bulkEntity2item(itemEntities.values().stream()
                .filter(obj -> obj.getOwner() == ownerId)
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public ArrayList<Item> findByText(String text) {
        return itemMapper.bulkEntity2item(itemEntities.values().stream()
                .filter(ItemEntity::getAvailable)
                .filter(obj -> obj.isItemRelevantForText(Pattern.compile(text.isBlank() ? "" : "^.*" + text + ".*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)))
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}
