package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.practicum.shareit.utils.AppConfiguration.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

    @PostMapping
    public ItemDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull int requestorId,
                              @Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq
    ) throws Throwable {
        log.info("requestorId={}, POST /items/ {}", requestorId, dtoReq);
        return itemMapper.model2dtoResp(itemService.create(requestorId, itemMapper.dtoReq2model(dtoReq)));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull int requestorId,
                                 @Valid @PathVariable int itemId,
                                 @Valid @RequestBody CommentDtoReq dtoReq
    ) throws Throwable {
        log.info("requestorId={}, POST /items/{}/comment {}", requestorId, itemId, dtoReq);
        return commentMapper.entity2dtoResp(itemService.create(requestorId, itemId, dtoReq));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull int requestorId,
                              @RequestBody ItemDtoReq dtoReq,
                              @PathVariable int itemId) {
        log.info("requestorId={}, PATCH /items/{}, {}", requestorId, itemId, dtoReq);
        return itemMapper.model2dtoResp(itemService.update(requestorId, itemMapper.dtoReq2model(dtoReq), itemId));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull int requestorId,
                                @PathVariable int itemId) {
        log.info("requestorId={}, GET /items/{}", requestorId, itemId);
        return itemMapper.model2dtoResp(itemService.findById(requestorId, itemId));
    }

    @GetMapping
    public List<ItemDtoResp> findByOwner(@RequestHeader(HEADER_USER_ID) int requestorId) {
        log.info("requestorId={} GET /items", requestorId);
        return itemMapper.bulkModel2dtoResp(itemService.findByOwner(requestorId));
    }

    @GetMapping("/search")
    public List<ItemDtoResp> findByText(@RequestHeader(HEADER_USER_ID) @NotNull int requestorId,
                                        @RequestParam(value = "text") String text) {
        log.info("requestorId={}, GET /search?text={}", requestorId, text);
        return itemMapper.bulkModel2dtoResp(itemService.findByText(requestorId, text));
    }
}
