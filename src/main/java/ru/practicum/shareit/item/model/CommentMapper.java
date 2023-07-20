package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentEntity dtoReq2entity(CommentDtoReq dtoReq, UserEntity userEntity, ItemEntity itemEntity) {
        var entity = Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, CommentEntity.class)).orElse(null);
        assert entity != null;
        entity.setCreated(LocalDateTime.now());
        entity.setUser(userEntity);
        entity.setItem(itemEntity);
        return entity;
    }

    public CommentDtoResp entity2dtoResp(CommentEntity entity) {
        var droResp = Optional.ofNullable(entity).map(obj -> modelMapper.map(obj, CommentDtoResp.class)).orElse(null);
        return droResp;
    }

}
