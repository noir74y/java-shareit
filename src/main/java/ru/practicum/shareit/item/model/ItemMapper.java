package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    public Item dtoReq2model(ItemDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Item.class)).orElse(null);
    }

    public ItemDtoResp model2dtoResp(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemDtoResp.class)).orElse(null);
    }

    public ItemEntity model2entity(Item model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemEntity.class)).orElse(null);
    }

    public ItemEntity model2entity(Item model, UserEntity userEntity) {
        ItemEntity entity;
        try {
            entity = Optional.ofNullable(model).map(obj -> modelMapper.map(obj, ItemEntity.class)).orElseThrow();
            entity.setOwner(userEntity);
        } catch (NoSuchElementException e) {
            entity = null;
        }
        return entity;
    }

    public Item entity2model(ItemEntity entity) {
        Item item;
        try {
            item = Optional.ofNullable(entity).map(obj -> modelMapper.map(obj, Item.class)).orElseThrow();
            item.setOwnerId(entity.getOwner().getId());
        } catch (NoSuchElementException e) {
            item = null;
        }
        return item;
    }

    public ArrayList<ItemDtoResp> bulkModel2dtoResp(Collection<Item> models) {
        return models.stream().map(this::model2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Item> bulkEntity2model(Collection<ItemEntity> entities) {
        return entities.stream().map(this::entity2model).collect(Collectors.toCollection(ArrayList::new));
    }
}
