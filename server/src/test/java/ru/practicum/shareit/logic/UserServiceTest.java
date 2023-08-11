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
import ru.practicum.shareit.model.user.User;
import ru.practicum.shareit.model.user.UserEntity;
import ru.practicum.shareit.repository.UserRepository;
import ru.practicum.shareit.service.impl.UserServiceImpl;
import ru.practicum.shareit.util.exception.NotFoundException;
import ru.practicum.shareit.util.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(UserServiceImpl.class)
@Import({UserMapper.class, ModelMapper.class})
public class UserServiceTest {
    int userId = 1;
    @Autowired
    private UserServiceImpl service;
    @MockBean
    private UserRepository repository;
    private User model;
    private UserEntity entity;

    @BeforeEach
    void setUp() {
        model = User.builder().id(userId).name("user").email("user@user.com").build();
        entity = UserEntity.builder().id(model.getId()).name(model.getName()).email(model.getEmail()).build();
    }

    @Test
    void create() throws Throwable {
        when(repository.save(any())).thenReturn(entity);

        assertThat(
                service.create(model),
                equalTo(model)
        );

        Mockito.verify(repository, Mockito.times(1)).save(entity);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void update() throws Throwable {
        var changedModel = User.builder().id(userId).name("user2").email("user2@user.com").build();
        var changedEntity = UserEntity.builder().id(userId).name("user2").email("user2@user.com").build();

        when(repository.findById(any())).thenReturn(Optional.ofNullable(entity));
        when(repository.save(any())).thenReturn(changedEntity);

        assertThat(
                service.update(changedModel, userId),
                equalTo(changedModel)
        );

        Mockito.verify(repository, Mockito.times(1)).save(changedEntity);
        Mockito.verify(repository, Mockito.times(1)).findById(userId);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(anyInt());
        service.delete(userId);
        Mockito.verify(repository, Mockito.times(1)).deleteAllById(any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void findById() throws Throwable {
        when(repository.findById(any())).thenReturn(Optional.ofNullable(entity));

        assertThat(
                service.findById(userId),
                equalTo(model)
        );

        Mockito.verify(repository, Mockito.times(1)).findById(userId);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void findById_NotFound() throws Throwable {
        int wrongUserId = 2;
        when(repository.findById(any())).thenThrow(new NotFoundException("no user with such id", String.valueOf(wrongUserId)));

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> service.findById(wrongUserId));

        Mockito.verify(repository, Mockito.times(1)).findById(wrongUserId);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        var referenceModelList = List.of(model);
        var referenceEntityList = List.of(entity);
        when(repository.findAll()).thenReturn(referenceEntityList);

        assertThat(
                service.findAll(),
                equalTo(referenceModelList)
        );

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

}
