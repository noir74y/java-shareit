package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@JsonTest
@Import({BookingMapper.class, ModelMapper.class, UserMapper.class})
public class BookingMapperTest {
    private static final UserDtoReq userDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
    private static final UserEntity userEntity = UserEntity.builder().name(userDtoReq.getName()).email(userDtoReq.getEmail()).build();
    private static final ItemDtoReq itemDtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
    private static final ItemEntity itemEntity = ItemEntity.builder().name(itemDtoReq.getName()).description(itemDtoReq.getDescription()).available(itemDtoReq.getAvailable()).owner(userEntity).build();

    private static final BookingDtoReq dtoReq = BookingDtoReq.builder().itemId(1).startDate(LocalDateTime.now()).endDate(LocalDateTime.now().plusDays(1)).build();
    private static final BookingDtoResp dtoResp = BookingDtoResp.builder().startDate(dtoReq.getStartDate()).endDate(dtoReq.getEndDate()).build();
    private static final Booking model = Booking.builder().itemId(1).startDate(dtoReq.getStartDate()).endDate(dtoReq.getEndDate()).build();
    private static final BookingEntity entity = BookingEntity.builder().startDate(dtoReq.getStartDate()).endDate(dtoReq.getEndDate()).build();

    @Autowired
    protected BookingMapper bookingMapper;

    @Test
    public void bookingMapperTest() {
        assertThat(bookingMapper.dtoReq2model(dtoReq),
                equalTo(model));

        assertThat(bookingMapper.model2dtoResp(model),
                equalTo(dtoResp));

        assertThat(bookingMapper.model2entity(model, userEntity, itemEntity),
                equalTo(entity));

        assertThat(bookingMapper.entity2model(entity),
                equalTo(model));

        assertThat(bookingMapper.bulkModel2dtoResp(List.of(model)),
                equalTo(List.of(dtoResp)));

        assertThat(bookingMapper.bulkEntity2model(List.of(entity)),
                equalTo(List.of(model)));
    }
}
