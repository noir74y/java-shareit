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
import ru.practicum.shareit.item.model.ItemDtoResp;
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
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void update() throws Exception {
        var itemToBoBookedId = 2;
        var OwnerOfItem = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();
        var dtoResp = rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId);

        assertThat(
                rest.patch(baseUrl + dtoResp.getId() + "?approved=true", BookingDtoResp.class, OwnerOfItem),
                equalTo(BookingDtoResp.builder()
                        .id(dtoResp.getId())
                        .startDate(dtoResp.getStartDate())
                        .endDate(dtoResp.getEndDate())
                        .status(BookingStatus.APPROVED.name())
                        .booker(BookingDtoRespBooker.builder()
                                .id(requestorId).build())
                        .item(BookingDtoRespItem.builder()
                                .id(itemToBoBookedId)
                                .name(dtoResp.getItem().getName()).build()).build())
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
                equalTo(BookingDtoResp.builder()
                        .id(bookingIdToFind)
                        .startDate(dtoResp.getStartDate())
                        .endDate(dtoResp.getEndDate())
                        .status(dtoResp.getStatus())
                        .booker(BookingDtoRespBooker.builder()
                                .id(requestorId).build())
                        .item(BookingDtoRespItem.builder()
                                .id(itemToBoBookedId)
                                .name(dtoResp.getItem().getName()).build()).build())
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void findByBookerAndState() throws Exception {
        var itemToBoBookedId = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();
        var dtoResp = rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId);

        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        var bookingIdToFind = 1;
        assertThat(
                dtoRespList,
                equalTo(List.of(BookingDtoResp.builder()
                        .id(bookingIdToFind)
                        .startDate(dtoResp.getStartDate())
                        .endDate(dtoResp.getEndDate())
                        .status(dtoResp.getStatus())
                        .booker(BookingDtoRespBooker.builder()
                                .id(requestorId).build())
                        .item(BookingDtoRespItem.builder()
                                .id(itemToBoBookedId)
                                .name(dtoResp.getItem().getName()).build()).build()))
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void findByOwnerAndState() throws Exception {
        var itemToBoBookedId = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();
        var dtoResp = rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId);

        var ownerOfItem = 2;
        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + "owner", ownerOfItem),
                        new TypeReference<>() {
                        });

        var bookingIdToFind = 1;
        assertThat(
                dtoRespList,
                equalTo(List.of(BookingDtoResp.builder()
                        .id(bookingIdToFind)
                        .startDate(dtoResp.getStartDate())
                        .endDate(dtoResp.getEndDate())
                        .status(dtoResp.getStatus())
                        .booker(BookingDtoRespBooker.builder()
                                .id(requestorId).build())
                        .item(BookingDtoRespItem.builder()
                                .id(itemToBoBookedId)
                                .name(dtoResp.getItem().getName()).build()).build()))
        );
    }
}
