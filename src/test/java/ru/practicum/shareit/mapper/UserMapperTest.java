package ru.practicum.shareit.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.user.model.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@JsonTest
@Import({UserMapper.class, ModelMapper.class})
public class UserMapperTest {
    private static final UserDtoReq userDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
    private static final UserDtoResp userDtoResp = UserDtoResp.builder().name("user").email("user@user.com").build();
    private static final User userModel = User.builder().name("user").email("user@user.com").build();
    private static final UserEntity userEntity = UserEntity.builder().name("user").email("user@user.com").build();
    @Autowired
    protected UserMapper userMapper;

    @Test
    public void userMapperTest() {
        assertThat(userMapper.dtoReq2model(userDtoReq),
                equalTo(userModel));

        assertThat(userMapper.model2dtoResp(userModel),
                equalTo(userDtoResp));

        assertThat(userMapper.model2entity(userModel),
                equalTo(userEntity));

        assertThat(userMapper.entity2model(userEntity),
                equalTo(userModel));

        assertThat(userMapper.bulkModel2dtoResp(List.of(userModel)),
                equalTo(List.of(userDtoResp)));

        assertThat(userMapper.bulkEntity2model(List.of(userEntity)),
                equalTo(List.of(userModel)));
    }
}
