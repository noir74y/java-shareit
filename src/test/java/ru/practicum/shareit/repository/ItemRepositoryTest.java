package ru.practicum.shareit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemRepositoryTest {
    UserEntity owner;
    ItemEntity entity1;
    ItemEntity entity2;
    @Autowired
    private ItemRepository repository;

    @BeforeEach
    void setUp() {
        owner = UserEntity.builder().id(1).name("user1").email("user1@user.com").build();
        entity1 = ItemEntity.builder().id(1).name("Дрель").description("Простая дрель").available(true).owner(owner).build();
        entity2 = ItemEntity.builder().id(2).name("Дрель").description("Сложная дрель").available(true).owner(owner).build();
    }

    @Test
    @Sql({"/schema.sql", "/item_repository_test.sql"})
    void search() {
        assertThat(
                repository.search("Сложная"),
                equalTo(List.of(entity2))
        );
    }

//    @Test
//    @Sql({"/schema.sql"})
//    void delete() {
//        repository.deleteById(entity1.getId());
//        repository.delete(entity2);
//        assertThat(
//                repository.findAll(),
//                equalTo(Collections.emptyList())
//        );
//    }
//
//    @Test
//    @Sql({"/schema.sql"})
//    void findById() {
//        assertThat(
//                repository.findById(entity1.getId()).orElse(null),
//                equalTo(entity1)
//        );
//    }
//
//    @Test
//    @Sql({"/schema.sql"})
//    void findAll() {
//        assertThat(
//                repository.findAll(),
//                equalTo(List.of(entity1, entity2))
//        );
//    }

}