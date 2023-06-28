package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.UserEntity;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDto user2dto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User dto2user(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserEntity user2entity(User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    public User entity2user(UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    public List<UserDto> bulkUser2dto(List<User> users) {
        return users.stream().map(this::user2dto).collect(Collectors.toList());
    }

    public List<User> bulkEntity2user(List<UserEntity> entities) {
        return entities.stream().map(this::entity2user).collect(Collectors.toList());
    }
}
