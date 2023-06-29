package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userMapper.user2dto(userService.create(userMapper.dto2user(userDto)));
    }

    @PatchMapping("/{id}")
    public UserDto update(@Valid @RequestBody UserDto userDto, @PathVariable int id) {
        return userMapper.user2dto(userService.update(userMapper.dto2user(userDto), id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserDto find(@PathVariable int id) {
        return userMapper.user2dto(userService.find(id));
    }

    @GetMapping
    public ArrayList<UserDto> findAll() {
        return userMapper.bulkUser2dto(userService.findAll());
    }
}
