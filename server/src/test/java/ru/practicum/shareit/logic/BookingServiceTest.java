package ru.practicum.shareit.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.model.booking.Booking;
import ru.practicum.shareit.model.booking.BookingEntity;
import ru.practicum.shareit.model.booking.BookingStatus;
import ru.practicum.shareit.model.item.ItemEntity;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.repository.BookingRepository;
import ru.practicum.shareit.repository.ItemRepository;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.impl.BookingServiceImpl;
import ru.practicum.shareit.util.exception.CustomValidationException;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.util.mapper.BookingMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(BookingServiceImpl.class)
@Import({BookingMapper.class, ModelMapper.class})
public class BookingServiceTest {
    int requestorId;
    LocalDateTime tomorrow;
    LocalDateTime theDayAfterTomorrow;
    @Autowired
    private BookingServiceImpl service;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @Autowired
    private BookingMapper bookingMapper;
    private Booking model;
    private BookingEntity entity;
    private UserEntity booker;
    private ItemEntity item;

    @BeforeEach
    void setUp() {
        requestorId = 1;

        tomorrow = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.SECONDS);
        theDayAfterTomorrow = tomorrow.plusDays(1);

        booker = UserEntity.builder()
                .id(1)
                .name("user1")
                .email("user1@mail.com").build();

        item = ItemEntity.builder()
                .id(1)
                .name("name")
                .description("description")
                .available(true)
                .owner(booker).build();

        model = Booking.builder()
                .id(1)
                .itemId(item.getId())
                .startDate(tomorrow)
                .endDate(theDayAfterTomorrow)
                .itemName(item.getName())
                .bookerId(booker.getId())
                .status(BookingStatus.WAITING).build();

        entity = bookingMapper.model2entity(model, booker, item);
    }

    @Test
    void create_StartIsNotBeforeEnd() {
        model.setEndDate(tomorrow);
        model.setStartDate(theDayAfterTomorrow);

        CustomValidationException exception = Assertions.assertThrows(
                CustomValidationException.class,
                () -> service.create(1, model));

        Mockito.verifyNoInteractions(userRepository, itemRepository, bookingRepository);
    }

    @Test
    void create_RequestorIsTheOwnerOfItem() throws Throwable {
        requestorId = 2;
        booker.setId(requestorId);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.create(requestorId, model));

        Mockito.verify(userRepository, Mockito.times(1)).findById(requestorId);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(item.getId());
        Mockito.verifyNoMoreInteractions(userRepository, bookingRepository);
        Mockito.verifyNoInteractions(bookingRepository);
    }

    @Test
    void create_ItemIsNotAvailable() throws Throwable {
        requestorId = 2;
        item.setAvailable(false);
        entity = bookingMapper.model2entity(model, booker, item);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        CustomValidationException exception = Assertions.assertThrows(
                CustomValidationException.class,
                () -> service.create(requestorId, model));

        Mockito.verify(userRepository, Mockito.times(1)).findById(requestorId);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(item.getId());
        Mockito.verifyNoMoreInteractions(userRepository, bookingRepository);
        Mockito.verifyNoInteractions(bookingRepository);
    }

    @Test
    void update_BookingIsAlreadyApproved() throws Throwable {
        model.setStatus(BookingStatus.APPROVED);
        entity = bookingMapper.model2entity(model, booker, item);

        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        CustomValidationException exception = Assertions.assertThrows(
                CustomValidationException.class,
                () -> service.update(requestorId, model.getId(), true));

        Mockito.verify(bookingRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(bookingRepository);
        Mockito.verifyNoInteractions(userRepository, itemRepository);
    }

    @Test
    void update_ApproverIsNotAnOwnerOfItem() throws Throwable {
        requestorId = 2;

        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.update(requestorId, model.getId(), true));

        Mockito.verify(bookingRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(bookingRepository);
        Mockito.verifyNoInteractions(userRepository, itemRepository);
    }

    @Test
    void findById_UserIsNeitherBookerNorOwner() throws Throwable {
        requestorId = 3;
        model.setBookerId(2);
        entity = bookingMapper.model2entity(model, booker, item);

        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.findById(requestorId, model.getId()));

        Mockito.verify(bookingRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(bookingRepository);
        Mockito.verifyNoInteractions(userRepository, itemRepository);
    }
}
