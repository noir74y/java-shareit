package ru.practicum.shareit.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.booking.model.BookingDtoReq;
import ru.practicum.shareit.booking.model.BookingDtoResp;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.request.model.RequestMapper;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;


@Import(GenericRestMock.class)
abstract public class GenericTest {
    @Autowired
    protected GenericRestMock<UserDtoReq, UserDtoResp> userRestMock;
    @Autowired
    protected GenericRestMock<ItemDtoReq, ItemDtoResp> itemRestMock;
    @Autowired
    protected GenericRestMock<CommentDtoReq, CommentDtoResp> commentRestMock;
    @Autowired
    protected GenericRestMock<BookingDtoReq, BookingDtoResp> bookingRestMock;
    @Autowired
    protected GenericRestMock<RequestDtoReq, RequestDtoResp> requestRestMock;
    @MockBean
    protected UserService userService;
    @MockBean
    protected UserMapper userMapper;
    @MockBean
    protected ItemService itemService;
    @MockBean
    protected ItemMapper itemMapper;
    @MockBean
    protected CommentService commentService;
    @MockBean
    protected CommentMapper commentMapper;
    @MockBean
    protected BookingService bookingService;
    @MockBean
    protected BookingMapper bookingMapper;
    @MockBean
    protected RequestService requestService;
    @MockBean
    protected RequestMapper requestMapper;
}
