package ru.practicum.shareit.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.mock.RestMock;
import ru.practicum.shareit.mock.RestMockGeneric;
import ru.practicum.shareit.mock.ServiceMock;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@Import({RestMock.class, ServiceMock.class, UserMapper.class, ModelMapper.class})
public class UserControllerTest {
    @Autowired
    protected RestMock restMock;
    @Autowired
    protected ServiceMock serviceMock;
    private final String baseUrl = "/users/";
    RestMockGeneric<UserDtoReq, UserDtoResp> rest;
    int userId = 1;
    private UserService service;
    private UserDtoReq dtoReq;
    private User model;
    private UserDtoResp dtoResp;

    @BeforeEach
    void setUp() {
        service = serviceMock.getUserService();
        rest = restMock.getUserRest();
        dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        model = User.builder().id(userId).name("user").email("user@user.com").build();
        dtoResp = UserDtoResp.builder().id(userId).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(service.create(any())).thenReturn(model);

        assertThat(
                rest.post(baseUrl, dtoReq, UserDtoResp.class),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).create(any());
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void update() throws Throwable {
        when(service.update(any(), anyInt())).thenReturn(model);

        assertThat(
                rest.patch(baseUrl + userId, dtoReq, UserDtoResp.class),
                equalTo(dtoResp)
        );

        Mockito.verify(service, Mockito.times(1)).update(any(), anyInt());
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void delete() throws Exception {
        rest.delete(baseUrl + userId);

        Mockito.verify(service, Mockito.times(1)).delete(userId);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void find() throws Throwable {
        when(service.findById(userId)).thenReturn(model);

        assertThat(
                rest.get(baseUrl + userId, UserDtoResp.class),
                equalTo(dtoResp)

        );

        Mockito.verify(service, Mockito.times(1)).findById(userId);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    void findAll() throws Exception {
        List<User> referenceModelList = List.of(model, model);
        List<UserDtoResp> referenceUserDtoRespList = List.of(dtoResp, dtoResp);

        when(service.findAll()).thenReturn(referenceModelList);

        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        rest.get(baseUrl),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(referenceUserDtoRespList)

        );

        Mockito.verify(service, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(service);
    }

}
