package ru.practicum.shareit.repository;

import ru.practicum.shareit.model.item.ItemDtoResp;

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
                .available(getAvailable())
                .build();
    }
}

