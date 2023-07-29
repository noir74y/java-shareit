package ru.practicum.shareit.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.mock.MapperMock;
import ru.practicum.shareit.mock.RepositoryMock;
import ru.practicum.shareit.mock.RestMock;
import ru.practicum.shareit.mock.ServiceMock;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(UserServiceImpl.class)
@Import({MapperMock.class, RepositoryMock.class})
public class UserServiceTest {
    int userId = 1;
    @Autowired
    protected MapperMock mapperMock;
    @Autowired
    protected RepositoryMock repositoryMock;
    @Autowired
    private UserServiceImpl service;
    private UserMapper mapper;
    private UserRepository repository;
    private User referenceModel;
    private UserEntity referenceEntity;

    @BeforeEach
    void setUp() {
        mapper = mapperMock.getUserMapper();
        repository = repositoryMock.getUserRepository();
        referenceModel = User.builder().id(userId).name("user").email("user@user.com").build();
        referenceEntity = UserEntity.builder().id(userId).name("user").email("user@user.com").build();
    }

    @Test
    void create() throws Throwable {
        when(mapper.model2entity(referenceModel)).thenReturn(referenceEntity);
        when(mapper.entity2model(referenceEntity)).thenReturn(referenceModel);
        when(repository.save(referenceEntity)).thenReturn(referenceEntity);

        assertThat(
                service.create(referenceModel),
                equalTo(referenceModel)
        );

        Mockito.verify(mapper, Mockito.times(1)).model2entity(referenceModel);
        Mockito.verify(mapper, Mockito.times(1)).entity2model(referenceEntity);
        Mockito.verify(repository, Mockito.times(1)).save(referenceEntity);

        Mockito.verifyNoMoreInteractions(mapper, repository);
    }
}
