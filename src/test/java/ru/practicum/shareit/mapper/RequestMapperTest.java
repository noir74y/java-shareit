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
    private final static UserEntity userEntity = UserEntity.builder()
            .id(1)
            .name("user")
            .email("user@user.com")
            .build();
    private final static ItemDtoResp itemDtoResp = ItemDtoResp.builder()
            .id(1)
            .name("Дрель")
            .description("Простая дрель")
            .available(true)
            .build();
    private final static RequestDtoReq dtoReq = RequestDtoReq.builder()
            .description("хочу дрель")
            .build();
    private final static RequestEntity entity = RequestEntity.builder()
            .id(1)
            .description(dtoReq.getDescription())
            .requestorId(userEntity.getId())
            .created(LocalDateTime.now())
            .build();
    private final static RequestDtoResp dtoResp = RequestDtoResp.builder()
            .id(1)
            .description("хочу дрель")
            .created(entity.getCreated())
            .items(List.of(itemDtoResp))
            .build();
    @Autowired
    private RequestMapper requestMapper;

    @Test
    public void requestMapperTest() {

        assertThat(requestMapper.dtoReq2entity(dtoReq, userEntity.getId()),
                equalTo(entity));

        assertThat(requestMapper.entity2dtoResp(entity),
                equalTo(dtoResp));
    }
}
