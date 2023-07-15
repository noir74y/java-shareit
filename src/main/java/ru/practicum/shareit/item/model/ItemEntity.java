package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.model.UserEntity;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "items")
public class ItemEntity {
    private static Integer itemId = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Boolean available;

    @Transient
    private Integer ownerId; // for InMemory only

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;

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
