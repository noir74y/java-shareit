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
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql"})
    void create_WrongUser() throws Exception {
        requestorId = 100;
        var itemToBoBookedId = 2;
        var dtoReq = BookingDtoReq.builder().itemId(itemToBoBookedId).startDate(tomorrow).endDate(theDayAfterTomorrow).build();

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> rest.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId));
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void update() throws Exception {
        var itemToBoBookedId = 2;
        var ownerOfItem = 2;
        var dtoResp = rest.get(baseUrl + itemToBoBookedId, BookingDtoResp.class, requestorId);
        dtoResp.setStatus(BookingStatus.APPROVED.name());

        assertThat(
                rest.patch(baseUrl + dtoResp.getId() + "?approved=true", BookingDtoResp.class, ownerOfItem),
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
        var bookerOfItem = 1;

        assertThat(
                getDtoRespList("?state=FUTURE", bookerOfItem),
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("?state=CURRENT", bookerOfItem),
                equalTo(List.of(rest.get(baseUrl + 4, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("?state=PAST", bookerOfItem),
                equalTo(List.of(
                        rest.get(baseUrl + 6, BookingDtoResp.class, requestorId),
                        rest.get(baseUrl + 7, BookingDtoResp.class, requestorId))
                )
        );

        assertThat(
                getDtoRespList("?state=WAITING", bookerOfItem),
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("?state=REJECTED", bookerOfItem),
                equalTo(List.of(
                        rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)
                ))
        );

        assertThat(
                getDtoRespList("?state=ALL", bookerOfItem),
                equalTo(List.of
                        (rest.get(baseUrl + 2, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 4, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 6, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 7, BookingDtoResp.class, requestorId))
                )
        );

        AssertionError exception = Assertions.assertThrows(
                AssertionError.class,
                () -> getDtoRespList("?state=INCORRECT_STATE", bookerOfItem));
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void findByOwnerAndState() throws Exception {
        List<BookingDtoResp> dtoRespList;
        var ownerOfItem = 2;

        assertThat(
                getDtoRespList("owner?state=FUTURE", ownerOfItem),
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("owner?state=CURRENT", ownerOfItem),
                equalTo(List.of(rest.get(baseUrl + 4, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("owner?state=PAST", ownerOfItem),
                equalTo(List.of(
                        rest.get(baseUrl + 6, BookingDtoResp.class, requestorId),
                        rest.get(baseUrl + 7, BookingDtoResp.class, requestorId)
                ))
        );

        assertThat(
                getDtoRespList("owner?state=WAITING", ownerOfItem),
                equalTo(List.of(rest.get(baseUrl + 2, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("owner?state=REJECTED", ownerOfItem),
                equalTo(List.of(rest.get(baseUrl + 6, BookingDtoResp.class, requestorId)))
        );

        assertThat(
                getDtoRespList("owner?state=ALL", ownerOfItem),
                equalTo(List.of
                        (rest.get(baseUrl + 2, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 4, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 6, BookingDtoResp.class, requestorId),
                                rest.get(baseUrl + 7, BookingDtoResp.class, requestorId))
                )
        );
    }

    private List<BookingDtoResp> getDtoRespList(String suffix, Integer userId) throws Exception {
        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl + suffix, userId),
                        new TypeReference<>() {
                        });
        return dtoRespList;
    }
}
