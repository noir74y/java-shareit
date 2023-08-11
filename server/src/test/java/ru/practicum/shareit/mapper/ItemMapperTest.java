package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.model.item.Item;
import ru.practicum.shareit.model.item.ItemDtoReq;
import ru.practicum.shareit.model.item.ItemDtoResp;
import ru.practicum.shareit.model.item.ItemEntity;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.util.mapper.ItemMapper;
import ru.practicum.shareit.util.mapper.UserMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig({ItemMapper.class, ModelMapper.class, UserMapper.class})
public class ItemMapperTest {
    private final UserEntity userEntity = UserEntity.builder()
            .name("user")
            .email("user@user.com")
            .build();
    private final ItemDtoReq dtoReq = ItemDtoReq.builder()
            .name("name")
            .description("description")
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
