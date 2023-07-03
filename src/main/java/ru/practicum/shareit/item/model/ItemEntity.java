package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@RequiredArgsConstructor
public class ItemEntity {
    private static Integer itemId = 0;
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer owner;
    private Integer request;

    private static Integer getNewId() {
        return ++itemId;
    }

    public void setNewId() {
        this.id = ItemEntity.getNewId();
    }

    public boolean isItemRelevantForText(Pattern pattern) {
        Matcher matcherName = pattern.matcher(name);
        Matcher matcherDescription = pattern.matcher(description);
        return matcherName.matches() || matcherDescription.matches();
    }
}
