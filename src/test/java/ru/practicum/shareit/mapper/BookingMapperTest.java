package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@JsonTest
@Import({BookingMapper.class, ModelMapper.class, UserMapper.class})
public class BookingMapperTest {
    @Autowired
    protected BookingMapper bookingMapper;
    private final static UserEntity userEntity = UserEntity.builder()
            .id(1)
            .name("user")
            .email("user@user.com")
            .build();

    private final static ItemEntity itemEntity = ItemEntity.builder()
            .id(1)
            .name("Дрель")
            .description("Простая дрель")
            .available(true)
            .owner(userEntity)
            .build();

    private final static BookingDtoRespItem bookingDtoRespItem = BookingDtoRespItem.builder()
            .id(itemEntity.getId())
            .name(itemEntity.getName())
            .build();

    private final static BookingDtoRespBooker bookingDtoRespBooker = BookingDtoRespBooker.builder()
            .id(userEntity.getId())
            .build();

    private final static Booking model = Booking.builder()
            .id(1)
            .itemId(itemEntity.getId())
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusDays(1))
            .itemName(itemEntity.getName())
            .bookerId(userEntity.getId())
            .build();

    private final static BookingDtoReq dtoReq = BookingDtoReq.builder()
            .itemId(itemEntity.getId())
            .startDate(model.getStartDate())
            .endDate(model.getEndDate())
            .build();

    private final static BookingDtoResp dtoResp = BookingDtoResp.builder()
            .id(model.getId())
            .startDate(dtoReq.getStartDate())
            .endDate(dtoReq.getEndDate())
            .booker(bookingDtoRespBooker)
            .item(bookingDtoRespItem)
            .build();

    private final static BookingEntity entity = BookingEntity.builder()
            .id(model.getId())
            .startDate(model.getStartDate())
            .endDate(model.getEndDate())
            .booker(userEntity)
            .item(itemEntity)
            .build();

    @Test
    public void bookingMapperTest() {
        model.setItemName(null);
        model.setBookerId(null);
        assertThat(bookingMapper.dtoReq2model(dtoReq),
                equalTo(model));

        dtoResp.setBooker(BookingDtoRespBooker.builder().id(null).build());
        dtoResp.setItem(BookingDtoRespItem.builder().id(itemEntity.getId()).build());
        assertThat(bookingMapper.model2dtoResp(model),
                equalTo(dtoResp));

        assertThat(bookingMapper.model2entity(model, userEntity, itemEntity),
                equalTo(entity));

        model.setItemName(itemEntity.getName());
        model.setBookerId(userEntity.getId());
        assertThat(bookingMapper.entity2model(entity),
                equalTo(model));

        dtoResp.setBooker(BookingDtoRespBooker.builder().id(userEntity.getId()).build());
        dtoResp.setItem(BookingDtoRespItem.builder().id(itemEntity.getId()).name(itemEntity.getName()).build());
        assertThat(bookingMapper.bulkModel2dtoResp(List.of(model)),
                equalTo(List.of(dtoResp)));

        assertThat(bookingMapper.bulkEntity2model(List.of(entity)),
                equalTo(List.of(model)));
    }
}
