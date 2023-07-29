package ru.practicum.shareit.mock;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.BookingDtoReq;
import ru.practicum.shareit.booking.model.BookingDtoResp;
import ru.practicum.shareit.item.model.CommentDtoReq;
import ru.practicum.shareit.item.model.CommentDtoResp;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.request.model.RequestDtoReq;
import ru.practicum.shareit.request.model.RequestDtoResp;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;

@Data
@Component
@Import(RestMockGeneric.class)
public class RestMock {
    @Autowired
    private RestMockGeneric<UserDtoReq, UserDtoResp> userRest;
    @Autowired
    private RestMockGeneric<ItemDtoReq, ItemDtoResp> itemRest;
    @Autowired
    private RestMockGeneric<CommentDtoReq, CommentDtoResp> commentRest;
    @Autowired
    private RestMockGeneric<BookingDtoReq, BookingDtoResp> bookingRest;
    @Autowired
    private RestMockGeneric<RequestDtoReq, RequestDtoResp> requestRest;
}
