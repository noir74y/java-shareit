package ru.practicum.shareit.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.utils.validation.OnCreate;
import ru.practicum.shareit.utils.validation.OnUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoReq {
    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 255)
    @NotBlank(groups = {OnCreate.class}, message = "name is empty")
    private String name;

    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 255)
    @NotBlank(groups = {OnCreate.class}, message = "email is empty")
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;
}
