package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserEntity;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDtoResp user2dtoResp(User user) {
        return modelMapper.map(user, UserDtoResp.class);
    }

    public User dtoReq2user(UserDtoReq userDtoReq) {
        return modelMapper.map(userDtoReq, User.class);
    }

    public UserEntity user2entity(User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    public User entity2user(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    public ArrayList<UserDtoResp> bulkUser2dtoResp(Collection<User> users) {
        return users.stream().map(this::user2dtoResp).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<User> bulkEntity2user(Collection<UserEntity> entities) {
        return entities.stream().map(this::entity2user).collect(Collectors.toCollection(ArrayList::new));
    }
}
