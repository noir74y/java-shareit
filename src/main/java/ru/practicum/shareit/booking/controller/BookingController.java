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
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.utils.AppConstants.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                 @Valid @RequestBody BookingDtoReq dtoReq) throws Throwable {
        log.info("POST /bookings/ requesterId={}, {}", requesterId, dtoReq);
        return bookingMapper.model2dtoResp(bookingService.create(requesterId, bookingMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                 @PathVariable Integer bookingId,
                                 @RequestParam Boolean approved) throws Throwable {
        log.info("PATCH /bookings/ requesterId={}, approved={}, {}", requesterId, bookingId, approved);
        return bookingMapper.model2dtoResp(bookingService.update(requesterId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                   @PathVariable Integer bookingId) throws Throwable {
        log.info("GET /bookings/{} requesterId={}", bookingId, requesterId);
        return bookingMapper.model2dtoResp(bookingService.findById(requesterId, bookingId));
    }

    @GetMapping
    public List<BookingDtoResp> findByBookerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                                          @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state) {
        log.info("GET /bookings/?state={} requesterId={}", state, requesterId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByBookerAndState(requesterId, state));
    }

    @GetMapping("/owner")
    public List<BookingDtoResp> findByOwnerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                                    @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state) {
        log.info("GET /bookings/owner?state={} requesterId={}", state, requesterId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByOwnerAndState(requesterId, state));
    }
}
