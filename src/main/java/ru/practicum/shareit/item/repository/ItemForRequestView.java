package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.ItemDtoResp;

public interface ItemForRequestView {
    Integer getRequestId();

    Integer getItemId();

    String getName();

    String getDescription();

    Boolean getAvailable();

    default ItemDtoResp getItemDtoResp() {
        return ItemDtoResp.builder()
                .id(getItemId())
                .name(getName())
                .description(getDescription())
                .requestId(getRequestId())
                .build();
    }
}

