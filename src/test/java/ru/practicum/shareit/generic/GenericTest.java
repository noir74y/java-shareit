package ru.practicum.shareit.generic;

import lombok.RequiredArgsConstructor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class GenericTest {
}
