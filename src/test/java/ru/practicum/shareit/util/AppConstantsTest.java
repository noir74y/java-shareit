package ru.practicum.shareit.util;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.utils.AppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AppConstantsTest {
    @Test
    void checkAppConstants() {
        assertThat("X-Sharer-User-Id", equalTo(AppConfiguration.HEADER_USER_ID));
        assertThat("yyyy-MM-dd'T'hh:mm:ss", equalTo(AppConfiguration.DATE_TIME_FORMAT));
        assertThat("0", equalTo(AppConfiguration.OFFSET_DEFAULT));
        assertThat("999", equalTo(AppConfiguration.PAGE_SIZE_MAX));
    }
}
