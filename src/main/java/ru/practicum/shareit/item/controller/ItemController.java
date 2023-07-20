package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import static ru.practicum.shareit.utils.AppConstants.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ItemDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                              @Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq
    ) throws Throwable {
        log.info("requesterId={}, POST /items/ {}", requesterId, dtoReq);
        return itemMapper.model2dtoResp(itemService.create(requesterId, itemMapper.dtoReq2model(dtoReq)));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                                 @PathVariable int itemId,
                                 @Valid @RequestBody CommentDtoReq dtoReq
    ) throws Throwable {
        log.info("requesterId={}, POST /items/{}/comment {}", requesterId, itemId, dtoReq);
        return commentMapper.entity2dtoResp(commentService.create(requesterId, itemId, dtoReq));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                              @RequestBody ItemDtoReq dtoReq,
                              @PathVariable int itemId) {
        log.info("requesterId={}, PATCH /items/{}, {}", requesterId, itemId, dtoReq);
        return itemMapper.model2dtoResp(itemService.update(requesterId, itemMapper.dtoReq2model(dtoReq), itemId));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                                @PathVariable int itemId) {
        log.info("requesterId={}, GET /items/{}", requesterId, itemId);
        return itemMapper.model2dtoResp(itemService.findById(requesterId, itemId));
    }

    @GetMapping
    public ArrayList<ItemDtoResp> findByOwner(@RequestHeader(HEADER_USER_ID) int requesterId) {
        log.info("requesterId={} GET /items", requesterId);
        return itemMapper.bulkModel2dtoResp(itemService.findByOwner(requesterId));
    }

    @GetMapping("/search")
    public ArrayList<ItemDtoResp> findByText(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                                             @RequestParam(value = "text") String text) {
        log.info("requesterId={}, GET /search?text={}", requesterId, text);
        return itemMapper.bulkModel2dtoResp(itemService.findByText(requesterId, text));
    }
}
