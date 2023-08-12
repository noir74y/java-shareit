package ru.practicum.shareit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.mapper.BookingMapper;
import ru.practicum.shareit.model.booking.BookingDtoReq;
import ru.practicum.shareit.model.booking.BookingDtoResp;
import ru.practicum.shareit.model.booking.BookingState;
import ru.practicum.shareit.service.BookingService;
import ru.practicum.shareit.utils.AppConfiguration;
import ru.practicum.shareit.utils.validation.ValueOfEnumConstraint;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class BookingControllerServer {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping
    public BookingDtoResp create(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                 @RequestBody BookingDtoReq dtoReq) throws Throwable {
        log.info("requestorId={}, POST /bookings {}", requestorId, dtoReq);
        return bookingMapper.model2dtoResp(bookingService.create(requestorId, bookingMapper.dtoReq2model(dtoReq)));
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResp update(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                 @PathVariable Integer bookingId,
                                 @RequestParam Boolean approved) throws Throwable {
        log.info("requestorId={}, PATCH /bookings/{}  approved={}", requestorId, bookingId, approved);
        return bookingMapper.model2dtoResp(bookingService.update(requestorId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResp findById(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                   @PathVariable Integer bookingId) throws Throwable {
        log.info("requestorId={}, GET /bookings/{}", requestorId, bookingId);
        return bookingMapper.model2dtoResp(bookingService.findById(requestorId, bookingId));
    }

    @GetMapping
    public List<BookingDtoResp> findByBookerAndState(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                                     @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                     @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, name = "from") Integer offset,
                                                     @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, name = "size") Integer pageSize) {
        log.info("requestorId={}, GET /bookings/?state={}", requestorId, state);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByBookerAndState(requestorId, state, offset, pageSize));
    }

    @GetMapping("/owner")
    public List<BookingDtoResp> findByOwnerAndState(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                                    @RequestParam(required = false, defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                    @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, name = "from") Integer offset,
                                                    @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, name = "size") Integer pageSize) {
        log.info("requestorId, GET /bookings/owner?state={} requestorId={}", requestorId, state);
        return bookingMapper.bulkModel2dtoResp(bookingService.findByOwnerAndState(requestorId, state, offset, pageSize));
    }
}
