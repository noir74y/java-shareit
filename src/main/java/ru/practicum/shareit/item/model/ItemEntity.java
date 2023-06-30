package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@RequiredArgsConstructor
public class ItemEntity {
    private int id;
    private String name;
    private String description;
    private Boolean available;
    private int owner;
    private int request;

    public boolean isItemRelevantForText(Pattern pattern) {
        Matcher matcherName = pattern.matcher(name);
        Matcher matcherDescription = pattern.matcher(description);
        return matcherName.matches() || matcherDescription.matches();
    }
}
