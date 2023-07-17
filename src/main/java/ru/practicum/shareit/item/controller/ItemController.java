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
    public ItemDtoResp create(@Validated(OnCreate.class) @RequestBody ItemDtoReq dtoReq,
                              @RequestHeader(HEADER_USER_ID) @NotNull int ownerId) throws Throwable {
        log.info("POST /items/ {}", dtoReq);
        return itemMapper.model2dtoResp(itemService.create(itemMapper.dtoReq2model(dtoReq), ownerId));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestBody ItemDtoReq dtoReq,
                              @RequestHeader(HEADER_USER_ID) @NotNull int ownerId,
                              @PathVariable int itemId) {
        log.info("PATCH /items/" + itemId + " {}", dtoReq);
        return itemMapper.model2dtoResp(itemService.update(itemMapper.dtoReq2model(dtoReq), ownerId, itemId));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@PathVariable int itemId) {
        log.info("GET /items/" + itemId);
        return itemMapper.model2dtoResp(itemService.findById(itemId));
    }

    @GetMapping
    public ArrayList<ItemDtoResp> findByOwner(@RequestHeader(HEADER_USER_ID) int ownerId) {
        log.info("GET /items/" + ownerId);
        return itemMapper.bulkModel2dtoResp(itemService.findByOwner(ownerId));
    }

    @GetMapping("/search")
    public ArrayList<ItemDtoResp> findByText(@RequestParam(value = "text") String text) {
        log.info("GET /search/" + text);
        return itemMapper.bulkModel2dtoResp(itemService.findByText(text));
    }
}