package ru.practicum.shareit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.mapper.UserMapper;
import ru.practicum.shareit.model.user.UserDtoReq;
import ru.practicum.shareit.model.user.UserDtoResp;
import ru.practicum.shareit.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserControllerServer {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDtoResp create(@RequestBody UserDtoReq dtoReq) throws Throwable {
        log.info("POST /users/ {}", dtoReq);
        return userMapper.model2dtoResp(userService.create(userMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{userId}")
    public UserDtoResp update(@RequestBody UserDtoReq dtoReq,
                              @PathVariable int userId) throws Throwable {
        log.info("PATCH /users/" + userId + " {}", dtoReq);
        return userMapper.model2dtoResp(userService.update(userMapper.dtoReq2model(dtoReq), userId));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId) {
        log.info("DELETE /users/" + userId);
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public UserDtoResp find(@PathVariable int userId) throws Throwable {
        log.info("GET /users/" + userId);
        return userMapper.model2dtoResp(userService.findById(userId));
    }

    @GetMapping
    public List<UserDtoResp> findAll() {
        log.info("GET /users/");
        return userMapper.bulkModel2dtoResp(userService.findAll());
    }
}
