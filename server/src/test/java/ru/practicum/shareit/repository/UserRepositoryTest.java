package ru.practicum.shareit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.model.user.UserEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {
    UserEntity entity1;
    UserEntity entity2;
    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        entity1 = UserEntity.builder().id(1).name("user1").email("user1@user.com").build();
        entity2 = UserEntity.builder().id(2).name("user2").email("user2@user.com").build();
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql",})
    void update() {
        entity2.setName("user3");
        repository.save(entity2);
        assertThat(
                repository.findById(entity2.getId()).orElse(null),
                equalTo(entity2)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql",})
    void delete() {
        repository.deleteById(entity1.getId());
        repository.delete(entity2);
        assertThat(
                repository.findAll(),
                equalTo(Collections.emptyList())
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql",})
    void findById() {
        assertThat(
                repository.findById(entity1.getId()).orElse(null),
                equalTo(entity1)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql",})
    void findAll() {
        assertThat(
                repository.findAll(),
                equalTo(List.of(entity1, entity2))
        );
    }
}
