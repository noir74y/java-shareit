package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserEntity;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User dtoReq2user(UserDtoReq userDtoReq) {
        return Optional.ofNullable(userDtoReq).map(obj -> modelMapper.map(obj, User.class)).orElse(null);
    }

    public UserDtoResp user2dtoResp(User user) {
        return Optional.ofNullable(user).map(obj -> modelMapper.map(obj, UserDtoResp.class)).orElse(null);
    }

    public UserEntity user2entity(User user) {
        return Optional.ofNullable(user).map(obj -> modelMapper.map(obj, UserEntity.class)).orElse(null);
    }

    public User entity2user(UserEntity userEntity) {
        return Optional.ofNullable(userEntity).map(obj -> modelMapper.map(obj, User.class)).orElse(null);
    }

    public ArrayList<UserDtoResp> bulkUser2dtoResp(Collection<User> users) {
        return users.stream().map(this::user2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<User> bulkEntity2user(Collection<UserEntity> entities) {
        return entities.stream().map(this::entity2user).collect(Collectors.toCollection(ArrayList::new));
    }
}
