package ru.practicum.shareit.request.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.CommentDtoReq;
import ru.practicum.shareit.item.model.CommentDtoResp;
import ru.practicum.shareit.item.model.CommentEntity;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ModelMapper modelMapper;

    public RequestEntity dtoReq2entity(RequestDtoReq dtoReq) {
        var entity = modelMapper.map(dtoReq, RequestEntity.class);
        Optional.ofNullable(entity).ifPresent(obj -> {
            obj.setCreated(LocalDateTime.now());
        });
        return entity;
    }

    public RequestDtoResp entity2dtoResp(RequestEntity entity) {
        return modelMapper.map(entity, RequestDtoResp.class);
    }
}
