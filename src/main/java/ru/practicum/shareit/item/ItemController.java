package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDtoReq;
import ru.practicum.shareit.item.model.ItemDtoResp;

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
    public ItemDtoResp create(ItemDtoReq itemDtoReq) {
        return null;
    }

    public ItemDtoResp update(ItemDtoReq itemDtoReq) {
        return null;
    }

    public ItemDtoResp findById(int itemId) {
        return null;
    }

    public ArrayList<ItemDtoResp> findByOwner(int userId) {
        return null;
    }

    public ArrayList<ItemDtoResp> findByText(String text) {
        return null;
    }
}
