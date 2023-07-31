package ru.practicum.shareit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.ItemForRequestView;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({ItemMapper.class, ModelMapper.class, UserMapper.class})
public class ItemRepositoryTest {
    UserEntity owner;
    ItemEntity entity1;
    ItemEntity entity2;
    @Autowired
    private ItemRepository repository;
    @Autowired
    private ItemMapper itemMapper;
    private ItemForRequestView itemForRequestView;

    @BeforeEach
    void setUp() {
        owner = UserEntity.builder().id(1).name("user1").email("user1@user.com").build();
        entity1 = ItemEntity.builder().id(1).name("Дрель").description("Простая дрель").available(true).owner(owner).requestId(1).build();
        entity2 = ItemEntity.builder().id(2).name("Дрель").description("Сложная дрель").available(true).owner(owner).requestId(2).build();
    }

    @Test
    @Sql({"/schema.sql", "/populate_data.sql"})
    void search() {
        assertThat(
                repository.search("Сложная"),
                equalTo(List.of(entity2))
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_data.sql"})
    void findAllByRequesterId() {
        assertThat(
                repository.findAllByRequesterId(2)
                        .stream()
                        .map(ItemForRequestView::getItemDtoResp)
                        .collect(Collectors.toList()),
                equalTo(List.of(itemMapper.model2dtoResp(itemMapper.entity2model(entity2))))
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_data.sql"})
    void findAllByRequestIdIn() {
        assertThat(
                new ArrayList<>(repository.findAllByRequestIdIn(Set.of(1))),
                equalTo(List.of(entity1))
        );
    }
}