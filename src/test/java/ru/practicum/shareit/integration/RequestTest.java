package ru.practicum.shareit.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.rest.RestMockGeneric;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RequestTest {
    private final String baseUrl = "/requests/";
    @Autowired
    RestMockGeneric<RequestDtoReq, RequestDtoResp> rest;
    Integer requestorId;

    @BeforeEach
    void setUp() {
        requestorId = 1;
    }

    @Test
    @Sql("/populate_users.sql")
    void create() throws Exception {
        var dtoReq = RequestDtoReq.builder().description("Хочу простую дрель").build();

        assertThat(
                rest.post(baseUrl, dtoReq, RequestDtoResp.class, requestorId).getDescription(),
                equalTo(dtoReq.getDescription())
        );
    }

    @Test
    @Sql("/populate_users.sql")
    void create_WrongUser() throws Exception {
        requestorId = 100;
        var dtoReq = RequestDtoReq.builder().description("Хочу простую дрель").build();

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> rest.post(baseUrl, dtoReq, RequestDtoResp.class, requestorId));
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void findAllByUser_WithItems() throws Exception {
        List<RequestDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, RequestDtoResp.class, requestorId)))
        );
    }


    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql"})
    void findAllByUser_WithoutItems() throws Exception {
        List<RequestDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, RequestDtoResp.class, requestorId)))
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql"})
    void findAllByOthers() throws Exception {
        List<RequestDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "all", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 1, RequestDtoResp.class, requestorId)))
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql"})
    void findById() throws Exception {
        requestorId = 2;
        assertThat(
                rest.get(baseUrl + 1, RequestDtoResp.class, requestorId),
                equalTo(RequestDtoResp.builder()
                        .id(1)
                        .description("Хочу сложную дрель")
                        .created(LocalDateTime.parse("2010-09-17T00:00"))
                        .items(Collections.emptyList()).build())
        );
    }
}
