package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.BookingDtoReq;
import ru.practicum.shareit.booking.model.BookingDtoResp;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.validation.OnCreate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import static ru.practicum.shareit.AppConstants.HEADER_USER_ID;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingDtoResp create(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                 @Validated(OnCreate.class) @RequestBody BookingDtoReq dtoReq) {
        log.info("POST /bookings/ requesterId={}, {}", requesterId, dtoReq);
        return bookingMapper.model2dtoResp(bookingService.create(requesterId, bookingMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{bookingId}?approved={approved}")
    public BookingDtoResp update(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                 @PathVariable Integer bookingId,
                                 @RequestParam Boolean approved) {
        log.info("POST /bookings/ requesterId={}, approved={}, {}", requesterId, bookingId, approved);
        return bookingMapper.model2dtoResp(bookingService.update(requesterId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResp findById(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                   @PathVariable Integer bookingId) {
        log.info("GET /bookings/{} requesterId={}", bookingId, requesterId);
        return bookingMapper.model2dtoResp(bookingService.findById(requesterId, bookingId));
    }

    @GetMapping("/?state={state}")
    public ArrayList<BookingDtoResp> findByBookerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                                          @RequestParam String state) {
        log.info("GET /bookings/?state={} requesterId={}", state, requesterId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByBookerAndState(requesterId, State.valueOf(state)));
    }

    @GetMapping("/owner?state={state}")
    public ArrayList<BookingDtoResp> findByOwnerAndState(@RequestHeader(HEADER_USER_ID) @NotNull Integer requesterId,
                                                         @RequestParam String state) {
        log.info("GET /bookings/owner?state={} requesterId={}", state, requesterId);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByOwnerAndState(requesterId, State.valueOf(state)));
    }
}
