package ru.practicum.shareit.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.utils.validation.OnCreate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoReq {
    @Size(max = 255)
    @NotBlank(groups = {OnCreate.class}, message = "name is empty")
    private String name;
    @Size(max = 255)
    @NotNull(groups = {OnCreate.class}, message = "description is absent")
    private String description;
    @NotNull(groups = {OnCreate.class}, message = "available is absent")
    private Boolean available;
    private Integer requestId;
}
