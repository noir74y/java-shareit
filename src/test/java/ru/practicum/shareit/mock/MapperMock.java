package ru.practicum.shareit.mock;

import lombok.Getter;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.item.model.CommentMapper;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.user.model.UserMapper;

@Getter
@Component
public class MapperMock {
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ItemMapper itemMapper;
    @MockBean
    private CommentMapper commentMapper;
    @MockBean
    private BookingMapper bookingMapper;
    @MockBean
    private RequestMapper requestMapper;
}
