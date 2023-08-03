package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.model.UserEntity;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig({CommentMapper.class, ModelMapper.class})
public class CommentMapperTest {
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
    private final static CommentDtoReq dtoReq = CommentDtoReq.builder()
            .text("хорошая дрель")
            .build();
    private final static CommentEntity entity = CommentEntity.builder()
            .id(1)
            .text(dtoReq.getText())
            .item(itemEntity)
            .author(userEntity)
            .created(LocalDateTime.now())
            .build();
    private final static CommentDtoResp dtoResp = CommentDtoResp.builder()
            .id(1)
            .text(dtoReq.getText())
            .authorName(userEntity.getName())
            .created(entity.getCreated())
            .build();
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void commentMapperTest() {

        var entityToCheck = commentMapper.dtoReq2entity(dtoReq, userEntity, itemEntity);

        assertNull(entityToCheck.getId());

        assertThat(entityToCheck.getText(),
                equalTo(entity.getText()));

        assertThat(entityToCheck.getItem().getId(),
                equalTo(entity.getItem().getId()));

        var dtoRespToCheck = commentMapper.entity2dtoResp(entity);

        assertThat(dtoRespToCheck,
                equalTo(dtoResp));

    }
}
