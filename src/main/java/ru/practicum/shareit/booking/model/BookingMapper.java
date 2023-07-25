package ru.practicum.shareit.booking.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ModelMapper modelMapper;

    public Booking dtoReq2model(BookingDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, Booking.class)).orElse(null);
    }

    public BookingDtoResp model2dtoResp(Booking model) {
        var dtoResp = Optional.ofNullable(model).map(obj -> modelMapper.map(obj, BookingDtoResp.class)).orElse(null);
        Optional.ofNullable(dtoResp).ifPresent(obj -> {
            obj.setBooker(BookingDtoRespBooker.builder().id(model.getBookerId()).build());
            obj.setItem(BookingDtoRespItem.builder().id(model.getItemId()).name(model.getItemName()).build());
        });
        return dtoResp;
    }

    public BookingEntity model2entity(Booking model, UserEntity userEntity, ItemEntity itemEntity) {
        var entity = Optional.ofNullable(model).map(obj -> modelMapper.map(obj, BookingEntity.class)).orElse(null);
        Optional.ofNullable(entity).ifPresent(obj -> {
            obj.setBooker(userEntity);
            obj.setItem(itemEntity);
        });
        return entity;
    }

    public Booking entity2model(BookingEntity entity) {
        var model = Optional.ofNullable(entity).map(obj -> modelMapper.map(obj, Booking.class)).orElse(null);
        Optional.ofNullable(model).ifPresent(obj -> {
            obj.setBookerId(entity.getBooker().getId());
            obj.setItemId(entity.getItem().getId());
            obj.setItemName(entity.getItem().getName());
        });
        return model;
    }

    public List<BookingDtoResp> bulkModel2dtoResp(Collection<Booking> models) {
        return models.stream().map(this::model2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Booking> bulkEntity2model(Collection<BookingEntity> entities) {
        return entities.stream().map(this::entity2model).collect(Collectors.toCollection(ArrayList::new));
    }
}
