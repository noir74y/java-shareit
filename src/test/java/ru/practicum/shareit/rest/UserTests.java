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
@Import({ServiceMock.class, MapperMock.class, RestMock.class})
public class UserTests {
    @Autowired
    private RestMock restMock;
    @Autowired
    private ServiceMock serviceMock;
    @Autowired
    private MapperMock mapperMock;
    private UserService userService;
    private UserMapper userMapper;
    private UserDtoReq referenceDtoReq;
    private User referenceModel;
    private UserDtoResp referenceDtoResp;
    private String baseUrl = "/users/";
    int userId = 1;

    @BeforeEach
    void setUp() {
        userMapper = mapperMock.getUserMapper();
        userService = serviceMock.getUserService();
        referenceDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        referenceModel = User.builder().id(userId).name("user").email("user@user.com").build();
        referenceDtoResp = UserDtoResp.builder().id(userId).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(userMapper.dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(userMapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(userService.create(userMapper.dtoReq2model(referenceDtoReq))).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().post(baseUrl, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(userMapper, Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(userMapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(userService, Mockito.times(1)).create(referenceModel);

        Mockito.verifyNoMoreInteractions(userMapper);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void update() throws Throwable {
        when(userMapper.dtoReq2model(referenceDtoReq)).thenReturn(referenceModel);
        when(userMapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(userService.update(userMapper.dtoReq2model(referenceDtoReq), userId)).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().patch(baseUrl + userId, referenceDtoReq, UserDtoResp.class),
                equalTo(referenceDtoResp)
        );

        Mockito.verify(userMapper, Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(userMapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(userService, Mockito.times(1)).update(referenceModel, userId);

        Mockito.verifyNoMoreInteractions(userMapper);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void delete() throws Exception {
        restMock.getUserRest().delete(baseUrl + userId);

        Mockito.verify(userService, Mockito.times(1)).delete(userId);

        Mockito.verifyNoMoreInteractions(userMapper);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void find() throws Throwable {
        when(userMapper.model2dtoResp(referenceModel)).thenReturn(referenceDtoResp);
        when(userService.findById(userId)).thenReturn(referenceModel);

        assertThat(
                restMock.getUserRest().get(baseUrl + userId, UserDtoResp.class),
                equalTo(referenceDtoResp)

        );

        Mockito.verify(userMapper, Mockito.times(1)).model2dtoResp(referenceModel);
        Mockito.verify(userService, Mockito.times(1)).findById(userId);

        Mockito.verifyNoMoreInteractions(userMapper);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void findAll() throws Exception {
        List<User> referenceModelList = List.of(referenceModel, referenceModel);
        List<UserDtoResp> referenceUserDtoRespList = List.of(referenceDtoResp, referenceDtoResp);

        when(userMapper.bulkModel2dtoResp(referenceModelList)).thenReturn(referenceUserDtoRespList);
        when(userService.findAll()).thenReturn(referenceModelList);

        List<UserDtoResp> dtoRespList = RestMockGeneric.getObjectMapper()
                .readValue(
                        restMock.getUserRest().get(baseUrl),
                        new TypeReference<>() {
                        });

        assertThat(
                dtoRespList,
                equalTo(referenceUserDtoRespList)

        );

        Mockito.verify(userMapper, Mockito.times(1)).bulkModel2dtoResp(referenceModelList);
        Mockito.verify(userService, Mockito.times(1)).findAll();

        Mockito.verifyNoMoreInteractions(userMapper);
        Mockito.verifyNoMoreInteractions(userService);
    }

}
