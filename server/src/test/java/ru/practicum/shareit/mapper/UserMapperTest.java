package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.model.user.User;
import ru.practicum.shareit.model.user.UserDtoReq;
import ru.practicum.shareit.model.user.UserDtoResp;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.util.mapper.UserMapper;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringJUnitConfig({UserMapper.class, ModelMapper.class})
public class UserMapperTest {
    private final UserDtoReq dtoReq = UserDtoReq.builder()
            .name("user")
            .email("user@user.com")
            .build();
    private final UserDtoResp dtoResp = UserDtoResp.builder()
            .name(dtoReq.getName())
            .email(dtoReq.getEmail())
            .build();
    private final User model = User.builder()
            .name(dtoReq.getName())
            .email(dtoReq.getEmail())
            .build();
    private final UserEntity entity = UserEntity.builder()
            .name(dtoReq.getName())
            .email(dtoReq.getEmail())
            .build();
    @Autowired
    private UserMapper userMapper;

    @Test
    public void userMapperTest() {
        assertThat(userMapper.dtoReq2model(dtoReq),
                equalTo(model));

        assertThat(userMapper.model2dtoResp(model),
                equalTo(dtoResp));

        assertThat(userMapper.model2entity(model),
                equalTo(entity));

        assertThat(userMapper.entity2model(entity),
                equalTo(model));

        assertThat(userMapper.bulkModel2dtoResp(List.of(model)),
                equalTo(List.of(dtoResp)));

        assertThat(userMapper.bulkEntity2model(List.of(entity)),
                equalTo(List.of(model)));
    }
}
