package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoReq {
    @NotBlank(groups = {OnCreate.class})
    private String name;
    @NotNull(groups = {OnCreate.class})
    private String description;
    @NotNull(groups = {OnCreate.class})
    private Boolean available;
}
