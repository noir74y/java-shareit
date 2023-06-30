package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @PostMapping
    public ItemDtoResp create(@Validated(OnCreate.class) @RequestBody ItemDtoReq itemDtoReq, @RequestHeader("X-Sharer-User-Id") @NotNull int ownerId) {
        return itemMapper.item2dtoResp(itemService.create(itemMapper.dtoReq2item(itemDtoReq), ownerId));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestBody ItemDtoReq itemDtoReq, @RequestHeader("X-Sharer-User-Id") @NotNull int ownerId, @PathVariable int itemId) {
        return itemMapper.item2dtoResp(itemService.update(itemMapper.dtoReq2item(itemDtoReq), ownerId, itemId));
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@PathVariable int itemId) {
        return itemMapper.item2dtoResp(itemService.findById(itemId));
    }

    @GetMapping
    public ArrayList<ItemDtoResp> findByOwner(@RequestHeader("X-Sharer-User-Id") int ownerId) {
        return itemMapper.bulkItem2dtoResp(itemService.findByOwner(ownerId));
    }

//    @GetMapping("/{itemId}")
//    public ArrayList<ItemDtoResp> findByText(String text) {
//        return itemMapper.bulkItem2dtoResp(itemService.findByText(text));
//    }
}
