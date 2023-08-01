package ru.practicum.shareit.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.rest.RestMockGeneric;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingTest {
    private final String baseUrl = "/bookings/";
    @Autowired
    RestMockGeneric<BookingDtoReq, BookingDtoResp> rest;
    Integer requestorId;
    LocalDateTime tomorrow;
    LocalDateTime theDayAfterTomorrow;

    @BeforeEach
    void setUp() {
        requestorId = 1;
        tomorrow = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.SECONDS);
        theDayAfterTomorrow = tomorrow.plusDays(1);
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void create() throws Exception {
        var itemToBoBookedId = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();

        assertThat(
                rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId),
                equalTo(BookingDtoResp.builder()
                        .id(1)
                        .startDate(dtoReq.getStartDate())
                        .endDate(dtoReq.getEndDate())
                        .status(BookingStatus.WAITING.name())
                        .booker(BookingDtoRespBooker.builder().id(requestorId).build())
                        .item(BookingDtoRespItem.builder().id(itemToBoBookedId).name("Дрель").build()).build())
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void update() throws Exception {
        var itemToBoBookedId = 2;
        var OwnerOfItem = 2;
        var dtoResp = rest.get(baseUrl + itemToBoBookedId, BookingDtoResp.class, requestorId);
        dtoResp.setStatus(BookingStatus.APPROVED.name());

        assertThat(
                rest.patch(baseUrl + dtoResp.getId() + "?approved=true", BookingDtoResp.class, OwnerOfItem),
                equalTo(dtoResp)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void findById() throws Exception {
        var itemToBoBookedId = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();
        var dtoResp = rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId);

        var bookingIdToFind = 1;
        assertThat(
                rest.get(baseUrl + bookingIdToFind, BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void findByBookerAndState() throws Exception {
        List<BookingDtoResp> dtoRespList;

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=FUTURE", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=CURRENT", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 4, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=PAST", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=WAITING", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=REJECTED", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "?state=ALL", requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of
                        (
                                rest.get(baseUrl + 2, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 4, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)
                        )
                )
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void findByOwnerAndState() throws Exception {
        List<BookingDtoResp> dtoRespList;
        var ownerOfItem = 2;

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=FUTURE", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=CURRENT", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 4, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=PAST", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=WAITING", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=REJECTED", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of(rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)))
        );

        dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner?state=ALL", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(List.of
                        (
                                rest.get(baseUrl + 2, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 4, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)
                        )
                )
        );
    }
}
