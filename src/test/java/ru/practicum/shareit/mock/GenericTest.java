package ru.practicum.shareit.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({ServiceMock.class, MapperMock.class, RestMock.class, RepositoryMock.class})
abstract public class GenericTest {
    @Autowired
    protected RestMock restMock;
    @Autowired
    protected ServiceMock serviceMock;
    @Autowired
    protected MapperMock mapperMock;
    @Autowired
    protected RepositoryMock repositoryMock;
}
