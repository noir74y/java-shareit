package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import static ru.practicum.shareit.AppConstants.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping
    public ItemDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                              @Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq
    ) throws Throwable {
        log.info("POST /items/ {}", dtoReq);
        return itemMapper.model2dtoResp(itemService.create(requesterId, itemMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                              @RequestBody ItemDtoReq dtoReq,
                              @PathVariable int itemId) {
        log.info("PATCH /items/" + itemId + " {}", dtoReq);
        return itemMapper.model2dtoResp(itemService.update(requesterId, itemMapper.dtoReq2model(dtoReq), itemId));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                                @PathVariable int itemId) {
        log.info("GET /items/" + itemId);
        return itemMapper.model2dtoResp(itemService.findById(requesterId, itemId));
    }

    @GetMapping
    public ArrayList<ItemDtoResp> findByOwner(@RequestHeader(HEADER_USER_ID) int requesterId) {
        log.info("GET /items/" + requesterId);
        return itemMapper.bulkModel2dtoResp(itemService.findByOwner(requesterId));
    }

    @GetMapping("/search")
    public ArrayList<ItemDtoResp> findByText(@RequestHeader(HEADER_USER_ID) @NotNull int requesterId,
                                             @RequestParam(value = "text") String text) {
        log.info("GET /search/" + text);
        return itemMapper.bulkModel2dtoResp(itemService.findByText(requesterId, text));
    }
}
