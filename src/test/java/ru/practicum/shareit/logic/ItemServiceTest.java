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
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.CommentMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ItemServiceImpl.class)
@Import({ItemMapper.class, ModelMapper.class, UserMapper.class, UserServiceImpl.class, CommentMapper.class})
public class ItemServiceTest {
    int userId = 1;
    @Autowired
    private ItemServiceImpl service;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookingRepository bookingRepository;
    @MockBean
    private CommentRepository commentRepository;

    private Item model;
    private ItemEntity entity;

//    @BeforeEach
//    void setUp() {
//        model = User.builder().id(userId).name("user").email("user@user.com").build();
//        entity = UserEntity.builder().id(userId).name("user").email("user@user.com").build();
//    }
//
    @Test
    void create() throws Throwable {
//        when(repository.save(any())).thenReturn(entity);
//
//        assertThat(
//                service.create(model),
//                equalTo(model)
//        );
//
//        Mockito.verify(repository, Mockito.times(1)).save(entity);
//        Mockito.verifyNoMoreInteractions(repository);
    }
//
//    @Test
//    void update() throws Throwable {
//        var changedUser = User.builder().id(userId).name("user2").email("user2@user.com").build();
//        var changedUserEntity = UserEntity.builder().id(userId).name("user2").email("user2@user.com").build();
//
//        when(repository.findById(any())).thenReturn(Optional.ofNullable(entity));
//        when(repository.save(any())).thenReturn(changedUserEntity);
//
//        var returnValue = service.update(changedUser, userId);
//
//        assertThat(
//                returnValue,
//                equalTo(changedUser)
//        );
//
//        Mockito.verify(repository, Mockito.times(1)).save(changedUserEntity);
//        Mockito.verify(repository, Mockito.times(1)).findById(userId);
//        Mockito.verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void delete() {
//        doNothing().when(repository).deleteById(anyInt());
//        service.delete(userId);
//        Mockito.verify(repository, Mockito.times(1)).deleteAllById(any());
//        Mockito.verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void findById() throws Throwable {
//        when(repository.findById(any())).thenReturn(Optional.ofNullable(entity));
//        service.findById(userId);
//        Mockito.verify(repository, Mockito.times(1)).findById(userId);
//        Mockito.verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void findById_NotFound() throws Throwable {
//        int wrongUserId = 2;
//        when(repository.findById(any())).thenThrow(new NotFoundException("no user with such id", String.valueOf(wrongUserId)));
//
//        NotFoundException exception = Assertions.assertThrows(
//                NotFoundException.class,
//                () -> service.findById(wrongUserId));
//
//        Mockito.verify(repository, Mockito.times(1)).findById(wrongUserId);
//        Mockito.verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void findAll() {
//        List<User> referenceModelList = List.of(model);
//        List<UserEntity> referenceEntityList = List.of(entity);
//        when(repository.findAll()).thenReturn(referenceEntityList);
//
//        assertThat(
//                service.findAll(),
//                equalTo(referenceModelList)
//        );
//
//        Mockito.verify(repository, Mockito.times(1)).findAll();
//        Mockito.verifyNoMoreInteractions(repository);
//    }

}
