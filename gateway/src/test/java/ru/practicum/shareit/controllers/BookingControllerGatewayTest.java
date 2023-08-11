package ru.practicum.shareit.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.clients.BookingClient;
import ru.practicum.shareit.model.booking.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BookingControllerGateway.class)
@Import({RestMockGeneric.class})
public class BookingControllerGatewayTest {
    private final String baseUrl = "/bookings/";
    @Autowired
    RestMockGeneric<BookingDtoReq, BookingDtoResp> restMock;
    int requestorId;
    LocalDateTime tomorrow;
    LocalDateTime theDayAfterTomorrow;
    @MockBean
    private BookingClient client;
    private BookingDtoReq dtoReq;
    private Booking model;
    private BookingDtoResp dtoResp;
    private ResponseEntity<Object> responseEntity;

    @BeforeEach
    void setUp() {
        requestorId = 1;
        var itemToBoBookedId = 2;
        tomorrow = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.SECONDS);
        theDayAfterTomorrow = tomorrow.plusDays(1);

        dtoReq = BookingDtoReq.builder()
                .itemId(itemToBoBookedId)
                .startDate(tomorrow)
                .endDate(theDayAfterTomorrow).build();

        model = Booking.builder()
                .id(1)
                .itemId(itemToBoBookedId)
                .startDate(tomorrow)
                .endDate(theDayAfterTomorrow)
                .itemName("itemName")
                .bookerId(requestorId)
                .status(BookingStatus.WAITING).build();

        dtoResp = BookingDtoResp.builder()
                .id(model.getId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .status(model.getStatus().name())
                .booker(BookingDtoRespBooker.builder()
                        .id(model.getBookerId()).build())
                .item(BookingDtoRespItem.builder()
                        .id(itemToBoBookedId)
                        .name(model.getItemName()).build()).build();

        responseEntity = new ResponseEntity<Object>(dtoResp, HttpStatus.OK);
    }

    @Test
    void create() throws Throwable {
        when(client.create(anyInt(), any())).thenReturn(responseEntity);

        assertThat(
                restMock.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).create(anyInt(), any());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void update() throws Throwable {
        when(client.update(anyInt(), any(), anyBoolean())).thenReturn(responseEntity);

        assertThat(
                restMock.patch(baseUrl + dtoResp.getId() + "?approved=true", BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).update(anyInt(), any(), anyBoolean());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findById() throws Throwable {
        when(client.findById(anyInt(), anyInt())).thenReturn(responseEntity);

        assertThat(
                restMock.get(baseUrl + dtoResp.getId(), BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).findById(anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findByBookerAndState() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(dtoResp), HttpStatus.OK);

        when(client.findByBookerAndState(anyInt(), anyString(), anyInt(), anyInt())).thenReturn(referenceDtoRespList);

        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1))
                .findByBookerAndState(anyInt(), anyString(), anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findByOwnerAndState() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(dtoResp), HttpStatus.OK);

        when(client.findByOwnerAndState(anyInt(), anyString(), anyInt(), anyInt())).thenReturn(referenceDtoRespList);

        var ownerOfItem = 2;
        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl + "owner", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1))
                .findByOwnerAndState(anyInt(), anyString(), anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

}
