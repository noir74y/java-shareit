package ru.practicum.shareit.booking.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
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
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, BookingDtoResp.class)).orElse(null);
    }

    public BookingEntity model2entity(Booking model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, BookingEntity.class)).orElse(null);
    }

    public BookingEntity model2entity(Booking model, User user, Item item) {
        return null;
    }

    public Booking entity2model(BookingEntity entity) {
        return null;
    }

    public ArrayList<BookingDtoResp> bulkModel2dtoResp(Collection<Booking> models) {
        return models.stream().map(this::model2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Booking> bulkEntity2model(Collection<BookingEntity> entities) {
        return entities.stream().map(this::entity2model).collect(Collectors.toCollection(ArrayList::new));
    }
}
