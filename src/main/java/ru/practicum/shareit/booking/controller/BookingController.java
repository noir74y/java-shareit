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
    public BookingDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                 @Valid @RequestBody BookingDtoReq dtoReq) throws Throwable {
        log.info("POST /bookings/ requestorId={}, {}", requestorId, dtoReq);
        return bookingMapper.model2dtoResp(bookingService.create(requestorId, bookingMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                 @PathVariable Integer bookingId,
                                 @RequestParam Boolean approved) throws Throwable {
        log.info("PATCH /bookings/ requestorId={}, approved={}, {}", requestorId, bookingId, approved);
        return bookingMapper.model2dtoResp(bookingService.update(requestorId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                   @PathVariable Integer bookingId) throws Throwable {
        log.info("GET /bookings/{} requestorId={}", bookingId, requestorId);
        return bookingMapper.model2dtoResp(bookingService.findById(requestorId, bookingId));
    }

    @GetMapping
    public List<BookingDtoResp> findByBookerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                          @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state) {
        log.info("GET /bookings/?state={} requestorId={}", state, requestorId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByBookerAndState(requestorId, state));
    }

    @GetMapping("/owner")
    public List<BookingDtoResp> findByOwnerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requestorId,
                                                    @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state) {
        log.info("GET /bookings/owner?state={} requestorId={}", state, requestorId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByOwnerAndState(requestorId, state));
    }
}
