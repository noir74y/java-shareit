package ru.practicum.shareit.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.controller.BookingController;
import ru.practicum.shareit.booking.model.*;
import ru.practicum.shareit.booking.service.BookingService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = BookingController.class)
@Import({RestMockGeneric.class, BookingMapper.class, ModelMapper.class})
public class BookingControllerTest {
    private final String baseUrl = "/bookings/";
    @Autowired
    RestMockGeneric<BookingDtoReq, BookingDtoResp> restMock;
    int requestorId;
    @MockBean
    private BookingService service;
    private BookingDtoReq dtoReq;
    private Booking model;
    private BookingDtoResp dtoResp;
    LocalDateTime tomorrow;
    LocalDateTime theDayAfterTomorrow;

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
                .itemName("Дрель")
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
    }

    @Test
    void create() throws Throwable {
        when(service.create(anyInt(), any())).thenReturn(model);

        assertThat(
                restMock.post(baseUrl, dtoReq, BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).create(anyInt(), any());
        Mockito.verifyNoMoreInteractions(service);
    }

//
//    @Test
//    void update() throws Throwable {
//        when(service.update(anyInt(), any(), anyInt())).thenReturn(model);
//
//        assertThat(
//                restMock.patch(baseUrl + dtoResp.getId(), dtoReq, ItemDtoResp.class, requestorId),
//                equalTo(dtoResp)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).update(anyInt(), any(), anyInt());
//        Mockito.verifyNoMoreInteractions(service);
//    }
//
//    @Test
//    void findById() throws Throwable {
//        when(service.findById(anyInt(), anyInt())).thenReturn(model);
//
//        assertThat(
//                restMock.get(baseUrl + dtoResp.getId(), ItemDtoResp.class, requestorId),
//                equalTo(dtoResp)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).findById(anyInt(), anyInt());
//        Mockito.verifyNoMoreInteractions(service);
//    }
//
//    @Test
//    void findByOwner() throws Exception {
//        var referenceModelList = List.of(model);
//        var referenceDtoRespList = List.of(dtoResp);
//
//        when(service.findByOwner(anyInt())).thenReturn(referenceModelList);
//
//        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
//                .readValue(
//                        restMock.get(baseUrl, requestorId),
//                        new TypeReference<>() {
//                        });
//
//        assertThat(
//                dtoRespList,
//                equalTo(referenceDtoRespList)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).findByOwner(anyInt());
//        Mockito.verifyNoMoreInteractions(service);
//    }
//
//    @Test
//    void findByText() throws Exception {
//        var referenceModelList = List.of(model);
//        var referenceDtoRespList = List.of(dtoResp);
//
//        when(service.findByText(anyInt(), anyString())).thenReturn(referenceModelList);
//
//        List<ItemDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
//                .readValue(
//                        restMock.get(baseUrl + "search?text=SomePattern", requestorId),
//                        new TypeReference<>() {
//                        });
//
//        assertThat(
//                dtoRespList,
//                equalTo(referenceDtoRespList)
//        );
//
//        Mockito.verify(service, Mockito.times(1)).findByText(anyInt(), anyString());
//        Mockito.verifyNoMoreInteractions(service);
//    }

}
