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
import ru.practicum.shareit.model.item.Item;
import ru.practicum.shareit.model.item.ItemEntity;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.repository.BookingRepository;
import ru.practicum.shareit.repository.CommentRepository;
import ru.practicum.shareit.repository.ItemRepository;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.impl.ItemServiceImpl;
import ru.practicum.shareit.service.impl.UserServiceImpl;
import ru.practicum.shareit.util.exception.ForbiddenException;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.util.mapper.CommentMapper;
import ru.practicum.shareit.util.mapper.ItemMapper;
import ru.practicum.shareit.util.mapper.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
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
        model = Item.builder().id(1).name("name").description("description").available(true).comments(Collections.emptyList()).ownerId(1).build();
        entity = ItemEntity.builder().id(model.getId()).name(model.getName()).description(model.getDescription()).available(model.getAvailable()).owner(owner).build();
    }

    @Test
    void create() throws Throwable {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(owner));
        when(itemRepository.save(any())).thenReturn(entity);
        model.setComments(null);

        assertThat(
                service.create(requestorId, model),
                equalTo(model)
        );

        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verify(itemRepository, Mockito.times(1)).save(entity);
        Mockito.verifyNoMoreInteractions(userRepository, itemRepository);
    }

    @Test
    void create_WrongUser() throws Throwable {
        requestorId = 2;
        when(userRepository.findById(anyInt())).thenThrow(new NotFoundException("no such user", String.valueOf(requestorId)));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.create(requestorId, model));

        Mockito.verify(userRepository, Mockito.times(1)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(userRepository);
        Mockito.verifyNoInteractions(itemRepository);
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

    @Test
    void update_WrongItem() throws Throwable {
        requestorId = 2;
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(entity));

        ForbiddenException exception = Assertions.assertThrows(
                ForbiddenException.class,
                () -> service.update(requestorId, model, model.getId()));

        Mockito.verify(itemRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void findById() throws Throwable {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.ofNullable(entity));

        assertThat(
                service.findById(requestorId, model.getId()),
                equalTo(model)
        );

        Mockito.verify(itemRepository, Mockito.times(1)).findById(model.getId());
        Mockito.verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void findByOwner() throws Throwable {
        when(itemRepository.findAllByOwnerIdOrderById(anyInt())).thenReturn(List.of(entity));

        assertThat(
                service.findByOwner(requestorId),
                equalTo(List.of(model))
        );

        Mockito.verify(itemRepository, Mockito.times(1)).findAllByOwnerIdOrderById(model.getId());
        Mockito.verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void findByText() throws Throwable {
        when(itemRepository.search(anyString())).thenReturn(List.of(entity));
        model.setComments(null);

        assertThat(
                service.findByText(requestorId, "SearchPattern"),
                equalTo(List.of(model))
        );

        Mockito.verify(itemRepository, Mockito.times(1)).search("SearchPattern");
        Mockito.verifyNoMoreInteractions(itemRepository);
    }
}
