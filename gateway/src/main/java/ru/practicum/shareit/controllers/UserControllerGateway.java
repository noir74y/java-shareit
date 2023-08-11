package ru.practicum.shareit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.clients.UserClient;
import ru.practicum.shareit.model.user.UserDtoReq;
import ru.practicum.shareit.utils.validation.OnCreate;
import ru.practicum.shareit.utils.validation.OnUpdate;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserControllerGateway {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> create(@Validated(OnCreate.class)
                                         @RequestBody UserDtoReq dtoReq) throws Throwable {
        log.info("POST /users/ {}", dtoReq);
        return userClient.create(dtoReq);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@Validated(OnUpdate.class)
                                         @RequestBody UserDtoReq dtoReq,
                                         @PathVariable Integer userId) throws Throwable {
        log.info("PATCH /users/" + userId + " {}", dtoReq);
        return userClient.update(dtoReq, userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Integer userId) {
        log.info("DELETE /users/" + userId);
        return userClient.delete(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> find(@PathVariable Integer userId) throws Throwable {
        log.info("GET /users/" + userId);
        return userClient.find(userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("GET /users/");
        return userClient.findAll();
    }
}
