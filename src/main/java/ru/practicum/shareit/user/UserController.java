package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.UserDtoReq;
import ru.practicum.shareit.user.model.UserDtoResp;
import ru.practicum.shareit.validation.OnCreate;
import ru.practicum.shareit.validation.OnUpdate;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDtoResp create(@Validated(OnCreate.class) @RequestBody UserDtoReq userDtoReq) {
        log.info("POST /users/ {}", userDtoReq);
        return userMapper.user2dtoResp(userService.create(userMapper.dtoReq2user(userDtoReq)));
    }

    @PatchMapping("/{userId}")
    public UserDtoResp update(@Validated(OnUpdate.class) @RequestBody UserDtoReq userDtoReq, @PathVariable int userId) {
        log.info("PATCH /users/" + userId + " {}", userDtoReq);
        return userMapper.user2dtoResp(userService.update(userMapper.dtoReq2user(userDtoReq), userId));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId) {
        log.info("DELETE /users/" + userId);
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public UserDtoResp find(@PathVariable int userId) {
        log.info("GET /users/" + userId);
        return userMapper.user2dtoResp(userService.findById(userId));
    }

    @GetMapping
    public ArrayList<UserDtoResp> findAll() {
        log.info("GET /users/");
        return userMapper.bulkUser2dtoResp(userService.findAll());
    }
}
