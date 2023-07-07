package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.item.model.ItemEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;

    public Item dtoReq2item(ItemDtoReq itemDtoReq) {
        return Optional.ofNullable(itemDtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ItemDtoResp item2dtoResp(Item item) {
        return Optional.ofNullable(item).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
    }

    public ItemEntity item2entity(Item item) {
        return Optional.ofNullable(item).map(obj -> modelMapper.map(obj, ItemEntity.class)).orElse(null);
    }

    public Item entity2item(ItemEntity itemEntity) {
        return Optional.ofNullable(itemEntity).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ArrayList<ItemDtoResp> bulkItem2dtoResp(Collection<Item> items) {
        return items.stream().map(this::item2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Item> bulkEntity2item(Collection<ItemEntity> entities) {
        return entities.stream().map(this::entity2item).collect(Collectors.toCollection(ArrayList::new));
    }
}
