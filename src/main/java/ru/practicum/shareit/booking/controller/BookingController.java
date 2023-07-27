package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.BookingDtoReq;
import ru.practicum.shareit.booking.model.BookingDtoResp;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.utils.validation.ValueOfEnumConstraint;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.practicum.shareit.utils.AppConstants.*;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                 @Valid @RequestBody BookingDtoReq dtoReq) throws Throwable {
        log.info("requestorId={}, POST /bookings {}", requestorId, dtoReq);
        return bookingMapper.model2dtoResp(bookingService.create(requestorId, bookingMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                 @PathVariable Integer bookingId,
                                 @RequestParam Boolean approved) throws Throwable {
        log.info("requestorId={}, PATCH /bookings/{}  approved={}", requestorId, bookingId, approved);
        return bookingMapper.model2dtoResp(bookingService.update(requestorId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                   @PathVariable Integer bookingId) throws Throwable {
        log.info("requestorId={}, GET /bookings/{}", requestorId, bookingId);
        return bookingMapper.model2dtoResp(bookingService.findById(requestorId, bookingId));
    }

    @GetMapping
    public List<BookingDtoResp> findByBookerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                     @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                     @RequestParam(defaultValue = OFFSET_DEFAULT, required = false, name = "from") @Min(0) Integer offset,
                                                     @RequestParam(defaultValue = PAGE_SIZE_MAX, required = false, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId={}, GET /bookings/?state={}", requestorId, state);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByBookerAndState(requestorId, state, offset, pageSize));
    }

    @GetMapping("/owner")
    public List<BookingDtoResp> findByOwnerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                    @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                    @RequestParam(defaultValue = OFFSET_DEFAULT, required = false, name = "from") @Min(0) Integer offset,
                                                    @RequestParam(defaultValue = PAGE_SIZE_MAX, required = false, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId, GET /bookings/owner?state={} requestorId={}", requestorId, state);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByOwnerAndState(requestorId, state, offset, pageSize));
    }
}
