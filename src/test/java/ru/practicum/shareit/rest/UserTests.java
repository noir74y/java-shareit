package ru.practicum.shareit.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.mock.MapperMock;
import ru.practicum.shareit.mock.RestMock;
import ru.practicum.shareit.mock.ServiceMock;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@Import({ServiceMock.class, MapperMock.class, RestMock.class})
public class UserTests {
    @Autowired
    ServiceMock serviceMock;
    @Autowired
    MapperMock mapperMock;
    @Autowired
    RestMock restMock;

    UserDtoReq referenceDtoReq;
    User referenceUser;
    UserDtoResp referenceDtoResp;

    @BeforeEach
    void setUp() {
        referenceDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        referenceUser = User.builder().id(1).name("user").email("user@user.com").build();
        referenceDtoResp = UserDtoResp.builder().id(1).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq)).thenReturn(referenceUser);
        when(mapperMock.getUserMapper().model2dtoResp(referenceUser)).thenReturn(referenceDtoResp);
        when(serviceMock.getUserService().create(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq))).thenReturn(referenceUser);

        assertThat(
                restMock.getUserRest().post("/users", referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).model2dtoResp(referenceUser);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).create(referenceUser);

        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

    @Test
    void update() throws Throwable {
        int userId = 1;

        when(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq)).thenReturn(referenceUser);
        when(mapperMock.getUserMapper().model2dtoResp(referenceUser)).thenReturn(referenceDtoResp);
        when(serviceMock.getUserService().update(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq), userId)).thenReturn(referenceUser);

        assertThat(
                restMock.getUserRest().patch("/users/" + userId, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).model2dtoResp(referenceUser);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).update(referenceUser, userId);

        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }
}
