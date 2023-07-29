package ru.practicum.shareit.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.mock.MapperMock;
import ru.practicum.shareit.mock.RestMock;
import ru.practicum.shareit.mock.RestMockGeneric;
import ru.practicum.shareit.mock.ServiceMock;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;

import java.util.ArrayList;
import java.util.List;

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
    User referenceModel;
    UserDtoResp referenceDtoResp;

    int userId = 1;
    String baseUrl = "/users/";

    @BeforeEach
    void setUp() {
        referenceDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        referenceModel = User.builder().id(userId).name("user").email("user@user.com").build();
        referenceDtoResp = UserDtoResp.builder().id(userId).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(mapperMock.getUserMapper().model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(serviceMock.getUserService().create(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq))).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().post(baseUrl, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).create(referenceModel);

        Mockito.verifyNoMoreInteractions(mapperMock.getUserMapper());
        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

    @Test
    void update() throws Throwable {
        when(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(mapperMock.getUserMapper().model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(serviceMock.getUserService().update(mapperMock.getUserMapper().dtoReq2model(referenceDtoReq), userId)).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().patch(baseUrl + userId, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).update(referenceModel, userId);

        Mockito.verifyNoMoreInteractions(mapperMock.getUserMapper());
        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

    @Test
    void delete() throws Exception {
        restMock.getUserRest().delete(baseUrl + userId);

        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).delete(userId);

        Mockito.verifyNoMoreInteractions(mapperMock.getUserMapper());
        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

    @Test
    void find() throws Throwable {
        when(mapperMock.getUserMapper().model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(serviceMock.getUserService().findById(userId)).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().get(baseUrl + userId, UserDtoResp.class),
                equalTo(referenceDtoResp)

        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).findById(userId);

        Mockito.verifyNoMoreInteractions(mapperMock.getUserMapper());
        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

    @Test
    void findAll() throws Exception {
        List<User> referenceModelList = List.of(referenceModel, referenceModel);
        List<UserDtoResp> referenceUserDtoRespList = List.of(referenceDtoResp, referenceDtoResp);

        when(mapperMock.getUserMapper().bulkModel2dtoResp(referenceModelList)).thenReturn(referenceUserDtoRespList);
        when(serviceMock.getUserService().findAll()).thenReturn(referenceModelList);

        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.getUserRest().get(baseUrl),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(referenceUserDtoRespList)

        );

        Mockito.verify(mapperMock.getUserMapper(), Mockito.times(1)).bulkModel2dtoResp(referenceModelList);
        Mockito.verify(serviceMock.getUserService(), Mockito.times(1)).findAll();

        Mockito.verifyNoMoreInteractions(mapperMock.getUserMapper());
        Mockito.verifyNoMoreInteractions(serviceMock.getUserService());
    }

}
