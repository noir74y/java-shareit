package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.intf.CreateCase;
import ru.practicum.shareit.user.model.intf.UpdateCase;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDtoResp create(@Validated(CreateCase.class) @RequestBody UserDtoReq userDtoReq) {
        return userMapper.user2dtoResp(userService.create(userMapper.dtoReq2user(userDtoReq)));
    }

    @PatchMapping("/{id}")
    public UserDtoResp update(@Validated(UpdateCase.class) @RequestBody UserDtoReq userDtoReq, @PathVariable int id) {
        return userMapper.user2dtoResp(userService.update(userMapper.dtoReq2user(userDtoReq), id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserDtoResp find(@PathVariable int id) {
        return userMapper.user2dtoResp(userService.find(id));
    }

    @GetMapping
    public ArrayList<UserDtoResp> findAll() {
        return userMapper.bulkUser2dtoResp(userService.findAll());
    }
}
