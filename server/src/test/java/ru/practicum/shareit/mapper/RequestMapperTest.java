package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.model.item.ItemDtoResp;
import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.model.request.RequestDtoResp;
import ru.practicum.shareit.model.request.RequestEntity;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.util.mapper.RequestMapper;

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
            .name("name")
            .description("description")
            .available(true)
            .build();
    private final RequestDtoReq dtoReq = RequestDtoReq.builder()
            .description("description")
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
