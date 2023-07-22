package ru.practicum.shareit.user.model;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User dtoReq2model(UserDtoReq dtoReq) {
        return Optional.ofNullable(dtoReq).map(obj -> modelMapper.map(obj, User.class)).orElse(null);
    }

    public UserDtoResp model2dtoResp(User model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, UserDtoResp.class)).orElse(null);
    }

    public UserEntity model2entity(User model) {
        return Optional.ofNullable(model).map(obj -> modelMapper.map(obj, UserEntity.class)).orElse(null);
    }

    public User entity2model(UserEntity entity) {
        return Optional.ofNullable(entity).map(obj -> modelMapper.map(obj, User.class)).orElse(null);
    }

    public ArrayList<UserDtoResp> bulkModel2dtoResp(Collection<User> models) {
        return models.stream().map(this::model2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<User> bulkEntity2model(Collection<UserEntity> entities) {
        return entities.stream().map(this::entity2model).collect(Collectors.toCollection(ArrayList::new));
    }
}
