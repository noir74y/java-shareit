package ru.practicum.shareit.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.*;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.rest.RestMockGeneric;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig({RequestMapper.class, ModelMapper.class, ItemRepository.class})
public class RequestMapperTest {

//  private    RequestMapper requestMapper;
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

    private final static RequestDtoReq requestDtoReq = RequestDtoReq.builder().description("хочу дрель").build();
    @Test
    public void requestMapperTest() {
    }
}
