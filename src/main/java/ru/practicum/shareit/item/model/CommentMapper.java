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
        var entity = modelMapper.map(dtoReq, CommentEntity.class);
        Optional.ofNullable(entity).ifPresent(obj -> {
            obj.setCreated(LocalDateTime.now());
            obj.setAuthor(userEntity);
            obj.setItem(itemEntity);
        });
        return entity;
    }

    public CommentDtoResp entity2dtoResp(CommentEntity entity) {
        var droResp = modelMapper.map(entity, CommentDtoResp.class);
        Optional.ofNullable(droResp).ifPresent(obj -> obj.setAuthorName(entity.getAuthor().getName()));
        return droResp;
    }
}
