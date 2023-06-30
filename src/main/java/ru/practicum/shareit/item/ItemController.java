package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;

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
    public ItemDtoResp create(@RequestBody ItemDtoReq itemDtoReq, @RequestHeader("X-Sharer-User-Id") @NotNull int userId) {
        return itemMapper.item2dtoResp(itemService.create(itemMapper.dtoReq2item(itemDtoReq), userId));
    }

    @PatchMapping("/{itemId}")
    public ItemDtoResp update(@RequestBody ItemDtoReq itemDtoReq, @PathVariable int itemId) {
        return null;
    }

    @GetMapping("/{itemId}")
    public ItemDtoResp findById(@PathVariable int itemId) {
        return null;
    }

    public ArrayList<ItemDtoResp> findByOwner(int ownerId) {
        return null;
    }

    public ArrayList<ItemDtoResp> findByText(String text) {
        return null;
    }
}
