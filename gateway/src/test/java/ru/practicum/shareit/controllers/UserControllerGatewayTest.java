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
import ru.practicum.shareit.clients.UserClient;
import ru.practicum.shareit.model.user.UserDtoReq;
import ru.practicum.shareit.model.user.UserDtoResp;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserControllerGateway.class)
@Import({RestMockGeneric.class})
public class UserControllerGatewayTest {
    private final String baseUrl = "/users/";
    @Autowired
    RestMockGeneric<UserDtoReq, UserDtoResp> restMock;
    int userId = 1;
    @MockBean
    private UserClient client;
    private UserDtoReq dtoReq;
    private UserDtoResp dtoResp;
    private ResponseEntity<Object> responseEntity;

    @BeforeEach
    void setUp() {
        dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        dtoResp = UserDtoResp.builder().id(userId).name(dtoReq.getName()).email(dtoReq.getEmail()).build();
        responseEntity = new ResponseEntity<Object>(dtoResp, HttpStatus.OK);
    }

    @Test
    void create() throws Throwable {
        when(client.create(any())).thenReturn(responseEntity);

        assertThat(
                restMock.post(baseUrl, dtoReq, UserDtoResp.class),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).create(any());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void update() throws Throwable {
        when(client.update(any(), anyInt())).thenReturn(responseEntity);

        assertThat(
                restMock.patch(baseUrl + userId, dtoReq, UserDtoResp.class),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).update(any(), anyInt());
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void delete() throws Exception {
        restMock.delete(baseUrl + userId);
        Mockito.verify(client, Mockito.times(1)).delete(userId);
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void find() throws Throwable {
        when(client.find(userId)).thenReturn(responseEntity);

        assertThat(
                restMock.get(baseUrl + userId, UserDtoResp.class),
                equalTo(dtoResp)
        );

        Mockito.verify(client, Mockito.times(1)).find(userId);
        Mockito.verifyNoMoreInteractions(client);
    }

    @Test
    void findAll() throws Exception {
        var referenceDtoRespList = new ResponseEntity<Object>(List.of(dtoResp), HttpStatus.OK);

        when(client.findAll()).thenReturn(referenceDtoRespList);

        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.get(baseUrl),
                        new TypeReference<>() {
                        });

        assertThat(
                new ResponseEntity<Object>(dtoRespList, HttpStatus.OK),
                equalTo(referenceDtoRespList)
        );

        Mockito.verify(client, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(client);
    }

}
