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
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.utils.exception.NotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ItemServiceImpl.class)
@Import({ItemMapper.class, ModelMapper.class, UserMapper.class, UserServiceImpl.class, CommentMapper.class})
public class ItemServiceTest {
    int requestorId = 1;
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

    private UserEntity owner;

    @BeforeEach
    void setUp() {
        owner = UserEntity.builder().id(1).name("user").email("user@user.com").build();
        model = Item.builder().id(1).name("Дрель").description("Простая дрель").available(true).ownerId(1).build();
        entity = ItemEntity.builder().id(model.getId()).name(model.getName()).description(model.getDescription()).available(model.getAvailable()).owner(owner).build();
    }

    @Test
    void create() throws Throwable {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(owner));
        when(itemRepository.save(any())).thenReturn(entity);

        assertThat(
                service.create(requestorId, model),
                equalTo(model)
        );

        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(itemRepository, Mockito.times(1)).save(entity);
        Mockito.verifyNoMoreInteractions(userRepository,itemRepository);
    }

    @Test
    void create_WrongUser() throws Throwable {
        requestorId = 2;
        when(userRepository.findById(anyInt())).thenThrow(new NotFoundException("no such user", String.valueOf(requestorId)));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.create(requestorId, model));

        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(userRepository,itemRepository);
    }

    @Test
    void update() throws Throwable {
        var changedModel = Item.builder().id(model.getId()).name(model.getName()).description("Сложная дрель").available(model.getAvailable()).ownerId(model.getOwnerId()).build();
        var changedEntity = ItemEntity.builder().id(changedModel.getId()).name(changedModel.getName()).description(changedModel.getDescription()).available(changedModel.getAvailable()).owner(owner).build();

        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(entity));
        when(itemRepository.save(any())).thenReturn(changedEntity);

        assertThat(
                service.update(requestorId, changedModel, changedModel.getId()),
                equalTo(changedModel)
        );

        Mockito.verify(itemRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verify(itemRepository, Mockito.times(1)).save(changedEntity);
        Mockito.verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void update_ItemNotFound() throws Throwable {
        when(itemRepository.findById(any())).thenThrow(new NotFoundException("no such item", String.valueOf(model.getId())));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.update(requestorId, model, model.getId()));

        Mockito.verify(itemRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(itemRepository);
    }


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
