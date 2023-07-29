package ru.practicum.shareit.mock;

import lombok.Getter;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

@Getter
@Component
public class RepositoryMock {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private RequestRepository requestRepository;
}
