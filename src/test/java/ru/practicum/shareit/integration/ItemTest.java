package ru.practicum.shareit.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.rest.RestMockGeneric;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;

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
    RestMockGeneric<ItemDtoReq, ItemDtoResp> rest;
    Integer requestorId;

    @BeforeEach
    void setUp() {
        requestorId = 1;
    }

    @Test
    @Sql(scripts = "/populate_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    @Sql(scripts = "/populate_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
    @Sql(scripts = "/populate_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        var dtoResp = rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);
        dtoResp.setComments(Collections.emptyList());

        assertThat(
                rest.get(baseUrl + dtoResp.getId(), ItemDtoResp.class, requestorId),
                equalTo(dtoResp)
        );
    }

    @Test
    @Sql(scripts = "/populate_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByOwner() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        requestorId = 2;

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(Collections.emptyList())
        );
    }

    @Test
    @Sql(scripts = "/populate_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findByText() throws Exception {
        var dtoReq = ItemDtoReq.builder().name("Дрель").description("Простая дрель").available(true).build();
        rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        dtoReq.setDescription("Сложная дрель");
        var dtoResp = rest.post(baseUrl, dtoReq, ItemDtoResp.class, requestorId);

        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "search?text=Сложная", requestorId),
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
