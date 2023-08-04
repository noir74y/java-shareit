package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoReq {
    @NotBlank(groups = {OnCreate.class}, message = "name is empty")
    private String name;
    @NotNull(groups = {OnCreate.class}, message = "description is absent")
    private String description;
    @NotNull(groups = {OnCreate.class}, message = "available is absent")
    private Boolean available;
    private Integer requestId;
}
