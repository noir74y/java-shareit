package ru.practicum.shareit.mock;

import lombok.Getter;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.service.UserService;

@Getter
@Component
public class ServiceMock {
    @MockBean
    private UserService userService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private RequestService requestService;
}
