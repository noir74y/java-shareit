package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.generic.GenericControllerMock;
import ru.practicum.shareit.generic.GenericTest;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private final UserDtoReq dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
    private final User user = User.builder().id(1).name("user").email("user@user.com").build();
    private UserDtoResp dtoResp = UserDtoResp.builder().id(1).name("user").email("user@user.com").build();

    @Test
    void create() throws Throwable {
        when(userMapper.dtoReq2model(any())).thenReturn(user);
        when(userMapper.model2dtoResp(any())).thenReturn(dtoResp);
        when(userService.create(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(dtoReq))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.id", is(dtoResp.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(dtoResp.getName())))
                .andExpect(jsonPath("$.email", is(dtoResp.getEmail())));

        Mockito.verify(userMapper, Mockito.times(1)).dtoReq2model(dtoReq);
        Mockito.verify(userMapper, Mockito.times(1)).model2dtoResp(user);
        Mockito.verify(userService, Mockito.times(1)).create(user);

        Mockito.verifyNoMoreInteractions(userService);
    }
}
