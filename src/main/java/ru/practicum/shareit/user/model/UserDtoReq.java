package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.utils.validation.OnCreate;
import ru.practicum.shareit.utils.validation.OnUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoReq {
    private String name;

    @NotBlank(groups = {OnCreate.class}, message = "email is empty")
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;
}
