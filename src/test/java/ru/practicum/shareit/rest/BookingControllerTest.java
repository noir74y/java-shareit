package ru.practicum.shareit.rest;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.utils.AppConstants.OFFSET_DEFAULT;
import static ru.practicum.shareit.utils.AppConstants.PAGE_SIZE_MAX;

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

    @Test
    void update() throws Throwable {
        when(service.update(anyInt(), any(), anyBoolean())).thenReturn(model);

        assertThat(
                restMock.patch(baseUrl + dtoResp.getId() + "?approved=true", BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).update(anyInt(), any(), anyBoolean());
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findById() throws Throwable {
        when(service.findById(anyInt(), anyInt())).thenReturn(model);

        assertThat(
                restMock.get(baseUrl + dtoResp.getId(), BookingDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).findById(anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findByBookerAndState() throws Exception {
        var referenceModelList = List.of(model);
        var referenceDtoRespList = List.of(dtoResp);

        when(service.findByBookerAndState(anyInt(), any(), anyInt(), anyInt())).thenReturn(referenceModelList);

        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(service, Mockito.times(1))
                .findByBookerAndState(requestorId, BookingState.ALL.name(), Integer.valueOf(OFFSET_DEFAULT), Integer.valueOf(PAGE_SIZE_MAX));
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findByOwnerAndState() throws Exception {
        var referenceModelList = List.of(model);
        var referenceDtoRespList = List.of(dtoResp);

        when(service.findByOwnerAndState(anyInt(), any(), anyInt(), anyInt())).thenReturn(referenceModelList);

        var ownerOfItem = 2;
        List<BookingDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl + "owner", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(service, Mockito.times(1))
                .findByOwnerAndState(ownerOfItem, BookingState.ALL.name(), Integer.valueOf(OFFSET_DEFAULT), Integer.valueOf(PAGE_SIZE_MAX));
        Mockito.verifyNoMoreInteractions(service);
    }
}
