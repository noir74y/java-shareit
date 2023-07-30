package ru.practicum.shareit.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.generic.RestMockGeneric;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    private final String baseUrl = "/users/";
    @Autowired
    RestMockGeneric<UserDtoReq, UserDtoResp> rest;
    UserDtoReq dtoReq;
    UserDtoResp dtoResp;
    List<UserDtoResp> dtoRespList;

    @Test
    void create() throws Exception {
        dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
        dtoResp = rest.post(baseUrl, dtoReq, UserDtoResp.class);
    }
}
