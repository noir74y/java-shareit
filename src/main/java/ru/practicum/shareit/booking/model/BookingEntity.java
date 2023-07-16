package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.ItemEntity;
import ru.practicum.shareit.user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booker_id")
    private UserEntity booker;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
