package ru.practicum.shareit.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.model.CommentDtoReq;
import ru.practicum.shareit.item.model.CommentDtoResp;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.rest.RestMockGeneric;
import ru.practicum.shareit.utils.exception.ForbiddenException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemTest {
    private final String baseUrl = "/items/";
    @Autowired
    RestMockGeneric<ItemDtoReq, ItemDtoResp> itemRest;
    @Autowired
    RestMockGeneric<CommentDtoReq, CommentDtoResp> commentRest;

    Integer requestorId;

    @BeforeEach
    void setUp() {
        requestorId = 1;
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void createItem() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();

        assertThat(
                itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId),
                equalTo(ItemDtoResp.builder()
                        .id(1)
                        .name(dtoReq.getName())
                        .description(dtoReq.getDescription())
                        .available(dtoReq.getAvailable()).build())
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void create_Fairy_Comment() throws Exception {
        requestorId = 1;
        var itemId = 2;
        var dtoReq = CommentDtoReq.builder().text("Хорошая дрель").build();
        var createdCommentId = 1;

        assertThat(
                commentRest.post(baseUrl + itemId + "/comment", dtoReq, CommentDtoResp.class, requestorId).getId(),
                equalTo(createdCommentId)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void create_Comment_Wrong_User() throws Exception {
        requestorId = 100;
        var itemId = 2;
        var dtoReq = CommentDtoReq.builder().text("Хорошая дрель").build();
        var createdCommentId = 1;

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> commentRest.post(baseUrl + itemId + "/comment", dtoReq, CommentDtoResp.class, requestorId));
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void create_Comment_Wrong_Item() throws Exception {
        requestorId = 1;
        var itemId = 100;
        var dtoReq = CommentDtoReq.builder().text("Хорошая дрель").build();
        var createdCommentId = 1;

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> commentRest.post(baseUrl + itemId + "/comment", dtoReq, CommentDtoResp.class, requestorId));
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void create_Unfair_Comment() throws Exception {
        requestorId = 2;
        var itemId = 1;
        var dtoReq = CommentDtoReq.builder().text("Хорошая дрель").build();
        var createdCommentId = 1;

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> commentRest.post(baseUrl + itemId + "/comment", dtoReq, CommentDtoResp.class, requestorId));
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void updateItem() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        dtoReq.setName("Дрель+");
        dtoReq.setDescription("Аккумуляторная дрель");
        dtoReq.setAvailable(false);

        assertThat(
                itemRest.patch(baseUrl + dtoResp.getId(), dtoReq, ItemDtoResp.class, requestorId),
                equalTo(ItemDtoResp.builder()
                        .id(dtoResp.getId())
                        .name(dtoReq.getName())
                        .description(dtoReq.getDescription())
                        .available(dtoReq.getAvailable()).build())
        );
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void updateWrongItem() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        dtoReq.setName("Дрель+");
        dtoReq.setDescription("Аккумуляторная дрель");
        dtoReq.setAvailable(false);

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> itemRest.patch(baseUrl + dtoResp.getId(), dtoReq, ItemDtoResp.class, 2));
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void findById() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);
        dtoResp.setComments(Collections.emptyList());

        assertThat(
                itemRest.get(baseUrl + dtoResp.getId(), ItemDtoResp.class, requestorId),
                equalTo(dtoResp)
        );
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void findByOwner() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        requestorId = 2;

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        itemRest.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(Collections.emptyList())
        );
    }

    @Test
    @Sql(scripts = "/populate_users.sql")
    void findByText() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        dtoReq.setDescription("Сложная дрель");
        var dtoResp = itemRest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        itemRest.get(baseUrl + "search?text=Сложная", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(ItemDtoResp.builder()
                        .id(dtoResp.getId())
                        .name(dtoResp.getName())
                        .description(dtoResp.getDescription())
                        .available(dtoResp.getAvailable()).build()))
        );
    }
}
