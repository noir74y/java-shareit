package ru.practicum.shareit.item.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Primary
public class ItemDaoImpl implements ItemDao {
    private final HashMap<Integer, ItemEntity> itemEntities;
    private final ItemMapper itemMapper;

    @Override
    public Item create(Item item) {
        ItemEntity itemEntity = itemMapper.model2entity(item);
        itemEntity.setNewId();
        itemEntities.put(itemEntity.getId(), itemEntity);
        return itemMapper.entity2model(itemEntity);
    }

    @Override
    public Item update(Item item) {
        itemEntities.replace(item.getId(), itemMapper.model2entity(item));
        return item;
    }

    @Override
    public Item findById(int itemId) {
        return itemMapper.entity2model(itemEntities.get(itemId));
    }

    @Override
    public ArrayList<Item> findByOwner(int ownerId) {
        return itemMapper.bulkEntity2model(itemEntities.values().stream()
                .filter(obj -> obj.getOwnerUserId().equals(ownerId))
                .collect(Collectors.toCollection(ArrayList::new)));
    }

    @Override
    public ArrayList<Item> findByText(String text) {
        Pattern pattern = Pattern.compile(text.isBlank() ? "" : "^.*" + text + ".*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        return itemMapper.bulkEntity2model(itemEntities.values().stream()
                .filter(ItemEntity::getAvailable)
                .filter(obj -> obj.isItemRelevantForText(pattern))
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}
