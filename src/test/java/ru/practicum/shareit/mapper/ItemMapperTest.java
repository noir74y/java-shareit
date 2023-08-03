package ru.practicum.shareit.mapper;

import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;

@JsonTest
@Import({ItemMapper.class, ModelMapper.class, UserMapper.class})
public class ItemMapperTest {
    private final UserEntity userEntity = UserEntity.builder()
            .name("user")
            .email("user@user.com")
            .build();
    private final ItemDtoReq dtoReq = ItemDtoReq.builder()
            .name("Дрель")
            .description("Простая дрель")
            .available(true)
            .build();
    private final ItemDtoResp dtoResp = ItemDtoResp.builder()
            .name(dtoReq.getName())
            .description(dtoReq.getDescription())
            .available(dtoReq.getAvailable())
            .build();
    private final Item model = Item.builder()
            .name(dtoReq.getName())
            .description(dtoReq.getDescription())
            .available(dtoReq.getAvailable())
            .build();
    private final ItemEntity entity = ItemEntity.builder()
            .name(dtoReq.getName())
            .description(dtoReq.getDescription())
            .available(dtoReq.getAvailable())
            .owner(userEntity)
            .build();

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void itemMapperTest() {
        assertThat(itemMapper.dtoReq2model(dtoReq),
                equalTo(model));

        assertThat(itemMapper.model2dtoResp(model),
                equalTo(dtoResp));

        assertThat(itemMapper.model2entity(model, userEntity),
                equalTo(entity));

        assertThat(itemMapper.entity2model(entity),
                equalTo(model));

        assertThat(itemMapper.bulkModel2dtoResp(List.of(model)),
                equalTo(List.of(dtoResp)));

        assertThat(itemMapper.bulkEntity2model(List.of(entity)),
                equalTo(List.of(model)));

        assertThat(itemMapper.bulkEntity2dtoResp(List.of(entity)),
                equalTo(List.of(dtoResp)));

       assertNull(itemMapper.model2entity(null, userEntity));

        assertNull(itemMapper.entity2model(null));
    }
}
