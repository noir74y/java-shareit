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
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@Import({RestMock.class, ServiceMock.class, MapperMock.class})
public class UserControllerTest {
    @Autowired
    protected RestMock restMock;
    @Autowired
    protected ServiceMock serviceMock;
    @Autowired
    protected MapperMock mapperMock;
    private final String baseUrl = "/users/";
    RestMockGeneric<UserDtoReq, UserDtoResp> rest;
    int userId = 1;
    private UserService service;
    private UserMapper mapper;
    private UserDtoReq referenceDtoReq;
    private User referenceModel;
    private UserDtoResp referenceDtoResp;

    @BeforeEach
    void setUp() {
        mapper = mapperMock.getUserMapper();
        service = serviceMock.getUserService();
        rest = restMock.getUserRest();
        referenceDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        referenceModel = User.builder().id(userId).name("user").email("user@user.com").build();
        referenceDtoResp = UserDtoResp.builder().id(userId).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(mapper.dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(mapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(service.create(mapper.dtoReq2model(referenceDtoReq))).thenReturn(referenceModel);

        assertThat(
                rest.post(baseUrl, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapper, Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(service, Mockito.times(1)).create(referenceModel);

        Mockito.verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void update() throws Throwable {
        when(mapper.dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(mapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(service.update(mapper.dtoReq2model(referenceDtoReq), userId)).thenReturn(referenceModel);

        assertThat(
                rest.patch(baseUrl + userId, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(mapper, Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(mapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(service, Mockito.times(1)).update(referenceModel, userId);

        Mockito.verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void delete() throws Exception {
        rest.delete(baseUrl + userId);

        Mockito.verify(service, Mockito.times(1)).delete(userId);

        Mockito.verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void find() throws Throwable {
        when(mapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(service.findById(userId)).thenReturn(referenceModel);

        assertThat(
                rest.get(baseUrl + userId, UserDtoResp.class),
                equalTo(referenceDtoResp)

        );

        Mockito.verify(mapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(service, Mockito.times(1)).findById(userId);

        Mockito.verifyNoMoreInteractions(mapper, service);
    }

    @Test
    void findAll() throws Exception {
        List<User> referenceModelList = List.of(referenceModel, referenceModel);
        List<UserDtoResp> referenceUserDtoRespList = List.of(referenceDtoResp, referenceDtoResp);

        when(mapper.bulkModel2dtoResp(referenceModelList)).thenReturn(referenceUserDtoRespList);
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

        Mockito.verify(mapper, Mockito.times(1)).bulkModel2dtoResp(referenceModelList);
        Mockito.verify(service, Mockito.times(1)).findAll();

        Mockito.verifyNoMoreInteractions(mapper, service);
    }

}
