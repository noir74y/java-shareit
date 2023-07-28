package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.generic.GenericControllerMock;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@Import(GenericControllerMock.class)
public class UserControllerTest {
    @Autowired
    private GenericControllerMock<UserDtoReq, UserDtoResp> controller;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    void create() throws Throwable {
        var referenceDtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        var referenceUser = User.builder().id(1).name("user").email("user@user.com").build();
        var referenceDtoResp = UserDtoResp.builder().id(1).name("user").email("user@user.com").build();

        when(userMapper.dtoReq2model(referenceDtoReq)).thenReturn(referenceUser);
        when(userMapper.model2dtoResp(referenceUser)).thenReturn(referenceDtoResp);
        when(userService.create(userMapper.dtoReq2model(referenceDtoReq))).thenReturn(referenceUser);

        var dtoResp = controller.post("/users", referenceDtoReq, UserDtoResp.class);
        assertThat(dtoResp, equalTo(referenceDtoResp));

        Mockito.verify(userMapper, Mockito.times(2)).dtoReq2model(referenceDtoReq);
        Mockito.verify(userMapper, Mockito.times(1)).model2dtoResp(referenceUser);
        Mockito.verify(userService, Mockito.times(1)).create(referenceUser);

        Mockito.verifyNoMoreInteractions(userService);
    }
}
