package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.generic.GenericControllerMock;
import ru.practicum.shareit.generic.GenericTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Component
@RequiredArgsConstructor
public class UserControllerTest extends GenericTest {
    @Mock
    private UserService userService;

    private final GenericControllerMock<UserDtoReq, UserDtoResp> userController;

    private final UserDtoReq dtoReq = UserDtoReq.builder().name("user").email("user@user.com").build();
    private final User user = User.builder().id(1).name("user").email("user@user.com").build();
    private UserDtoResp dtoResp;

    @Test
    void create() throws Throwable {
        when(userService.create(any()))
                .thenReturn(user);

        dtoResp = userController.post("/users", dtoReq, (Class<UserDtoResp>) dtoResp.getClass());

        //Mockito.verifyNoMoreInteractions(userService);
    }
}
