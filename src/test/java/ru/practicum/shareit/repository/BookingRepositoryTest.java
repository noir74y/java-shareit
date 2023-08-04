package ru.practicum.shareit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.booking.model.BookingEntity;
import ru.practicum.shareit.booking.repository.BookingRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingRepositoryTest {
    BookingEntity entity;
    @Autowired
    private BookingRepository repository;

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void getLastBooking() {
        assertThat(
                repository.getLastBooking(2, 2).getId(),
                equalTo(4)
        );
    }

    @Test
    @Sql({"/schema.sql", "/populate_users.sql", "/populate_requests.sql", "/populate_items.sql", "/populate_bookings.sql"})
    void getNextBooking() {
        assertThat(
                repository.getNextBooking(1, 1).getId(),
                equalTo(1)
        );
    }
}