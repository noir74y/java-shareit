package ru.practicum.shareit.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.generic.RestMockGeneric;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemTest {
    private final String baseUrl = "/items/";
    @Autowired
    RestMockGeneric<ItemDtoReq, ItemDtoResp> rest;
    Integer requestorId;

    @BeforeEach
    void setUp() {
        requestorId = 1;
    }

    @Test
    @Sql(scripts = "/populate-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void create() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();

        assertThat(
                rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId),
                equalTo(ItemDtoResp.builder()
                        .id(1)
                        .name(dtoReq.getName())
                        .description(dtoReq.getDescription())
                        .available(dtoReq.getAvailable()).build())
        );
    }

    @Test
    @Sql(scripts = "/populate-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void update() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        dtoReq.setName("Дрель+");
        dtoReq.setDescription("Аккумуляторная дрель");
        dtoReq.setAvailable(false);

        assertThat(
                rest.patch(baseUrl + dtoResp.getId(), dtoReq, ItemDtoResp.class, requestorId),
                equalTo(ItemDtoResp.builder()
                        .id(dtoResp.getId())
                        .name(dtoReq.getName())
                        .description(dtoReq.getDescription())
                        .available(dtoReq.getAvailable()).build())
        );
    }

    @Test
    @Sql(scripts = "/populate-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);
        dtoResp.setComments(Collections.emptyList());

        assertThat(
                rest.get(baseUrl + dtoResp.getId(), ItemDtoResp.class, requestorId),
                equalTo(dtoResp)
        );
    }


//    @Test
//    @Sql(scripts = "/populate-users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void findByOwner() throws Exception {
//        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
//        var dtoResp = rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);
//        dtoResp.setComments(Collections.emptyList());
//
//        assertThat(
//                rest.get(baseUrl + dtoResp.getId(), ItemDtoResp.class, requestorId),
//                equalTo(dtoResp)
//        );
//    }

//
//    @Test
//    void findAll() throws Exception {
//        var dtoReq1 = UserDtoReq.builder().name("user1").email("use1r@user.com").build();
//        var dtoResp1 = rest.post(baseUrl, dtoReq1, UserDtoResp.class);
//        var dtoReq2 = UserDtoReq.builder().name("user2").email("user2@user.com").build();
//        var dtoResp2 = rest.post(baseUrl, dtoReq2, UserDtoResp.class);
//
//        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
//                .readValue(
//                        rest.get(baseUrl),
//                        new TypeReference<>() {
//                        });
//
//        assertThat(
//                dtoRespList,
//                equalTo(List.of(dtoResp1, dtoResp2))
//        );
//    }
}
