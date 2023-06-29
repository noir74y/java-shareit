package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.intf.CreateCase;
import ru.practicum.shareit.user.model.intf.UpdateCase;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoReq {
    private String name;

    @NotBlank(groups = {CreateCase.class})
    @Email(groups = {CreateCase.class, UpdateCase.class})
    private String email;
}
