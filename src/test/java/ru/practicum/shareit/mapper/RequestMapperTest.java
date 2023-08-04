package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestEntity;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringJUnitConfig({RequestMapper.class, ModelMapper.class})
public class RequestMapperTest {
    private final UserEntity userEntity = UserEntity.builder()
            .id(1)
            .name("user")
            .email("user@user.com")
            .build();
    private final ItemDtoResp itemDtoResp = ItemDtoResp.builder()
            .id(1)
            .name("Дрель")
            .description("Простая дрель")
            .available(true)
            .build();
    private final RequestDtoReq dtoReq = RequestDtoReq.builder()
            .description("хочу дрель")
            .build();
    private final RequestEntity entity = RequestEntity.builder()
            .id(1)
            .description(dtoReq.getDescription())
            .requestorId(userEntity.getId())
            .created(LocalDateTime.now())
            .build();
    private final RequestDtoResp dtoResp = RequestDtoResp.builder()
            .id(1)
            .description(dtoReq.getDescription())
            .created(entity.getCreated())
            .items(List.of(itemDtoResp))
            .build();
    @Autowired
    private RequestMapper requestMapper;

    @Test
    public void requestMapperTest() {
        entity.setId(null);
        assertThat(requestMapper.dtoReq2entity(dtoReq, userEntity.getId()).getDescription(),
                equalTo(entity.getDescription()));

        entity.setId(1);
        dtoResp.setItems(null);
        assertThat(requestMapper.entity2dtoResp(entity),
                equalTo(dtoResp));
    }
}
