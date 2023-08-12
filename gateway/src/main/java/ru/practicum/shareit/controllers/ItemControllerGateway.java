package ru.practicum.shareit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.clients.ItemClient;
import ru.practicum.shareit.model.comment.CommentDtoReq;
import ru.practicum.shareit.model.item.ItemDtoReq;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static ru.practicum.shareit.utils.AppConfiguration.HEADER_USER_ID;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemControllerGateway {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                         @Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq
    ) {
        log.info("requestorId={}, POST /items/ {}", requestorId, dtoReq);
        return itemClient.create(requestorId, dtoReq);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                         @Valid @PathVariable Integer itemId,
                                         @Valid @RequestBody CommentDtoReq dtoReq
    ) {
        log.info("requestorId={}, POST /items/{}/comment {}", requestorId, itemId, dtoReq);
        return itemClient.create(requestorId, itemId, dtoReq);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                         @RequestBody ItemDtoReq dtoReq,
                                         @PathVariable Integer itemId) {
        log.info("requestorId={}, PATCH /items/{}, {}", requestorId, itemId, dtoReq);
        return itemClient.update(requestorId, itemId, dtoReq);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                           @PathVariable Integer itemId) {
        log.info("requestorId={}, GET /items/{}", requestorId, itemId);
        return itemClient.findById(requestorId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> findByOwner(@RequestHeader(HEADER_USER_ID) Integer requestorId) {
        log.info("requestorId={} GET /items", requestorId);
        return itemClient.findByOwner(requestorId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> findByText(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                             @RequestParam(value = "text") String text) {
        log.info("requestorId={}, GET /search?text={}", requestorId, text);
        return itemClient.findByText(requestorId, text);
    }
}
