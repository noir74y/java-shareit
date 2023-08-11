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
import ru.practicum.shareit.clients.RequestClient;
import ru.practicum.shareit.model.request.RequestDtoReq;
import ru.practicum.shareit.model.request.RequestDtoResp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RequestControllerGateway.class)
@Import({RestMockGeneric.class})
public class RequestControllerGatewayTest {
    private final String baseUrl = "/requests/";
    @Autowired
    RestMockGeneric<RequestDtoReq, RequestDtoResp> restMock;
    int requestorId;
    LocalDateTime created;
    @MockBean
    private RequestClient client;
    private RequestDtoReq dtoReq;
    private RequestDtoResp dtoResp;
    private ResponseEntity<Object> responseEntity;

    @BeforeEach
    void setUp() {
        requestorId = 1;
        var itemToBoBookedId = 2;
        created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        dtoReq = RequestDtoReq.builder()
                .description("request")
                .build();

        dtoResp = RequestDtoResp.builder()
                .id(1)
                .description(dtoReq.getDescription())
                .created(created)
                .build();

        responseEntity = new ResponseEntity<Object>(dtoResp, HttpStatus.OK);
    }

    @Test
    void create() throws Throwable {
        when(client.create(anyInt(), any())).thenReturn(responseEntity);

        assertThat(
                restMock.post(baseUrl, dtoReq, RequestDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).create(anyInt(), any());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findAllByUser() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(dtoResp), HttpStatus.OK);

        when(client.findAllByUser(anyInt())).thenReturn(referenceDtoRespList);

        List<RequestDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl, requestorId),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1))
                .findAllByUser(anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findByOwnerAndState() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(dtoResp), HttpStatus.OK);

        when(client.findAllByOthers(anyInt(), anyInt(), anyInt())).thenReturn(referenceDtoRespList);

        var ownerOfItem = 2;
        List<RequestDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl + "all", ownerOfItem),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1))
                .findAllByOthers(anyInt(), anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findById() throws Throwable {
        when(client.findById(anyInt(), anyInt())).thenReturn(responseEntity);

        assertThat(
                restMock.get(baseUrl + dtoResp.getId(), RequestDtoResp.class, requestorId),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).findById(anyInt(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }
}
