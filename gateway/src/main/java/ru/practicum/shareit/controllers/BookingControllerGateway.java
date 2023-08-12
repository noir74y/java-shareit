package ru.practicum.shareit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.clients.BookingClient;
import ru.practicum.shareit.model.booking.BookingDtoReq;
import ru.practicum.shareit.model.booking.BookingState;
import ru.practicum.shareit.utils.AppConfiguration;
import ru.practicum.shareit.utils.exception.ErrorMessage;
import ru.practicum.shareit.utils.validation.ValueOfEnumConstraint;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class BookingControllerGateway {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                         @Valid @RequestBody BookingDtoReq dtoReq) {
        log.info("requestorId={}, POST /bookings {}", requestorId, dtoReq);
        return bookingClient.create(requestorId, dtoReq);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                         @PathVariable Integer bookingId,
                                         @RequestParam Boolean approved) {
        log.info("requestorId={}, PATCH /bookings/{}  approved={}", requestorId, bookingId, approved);
        return bookingClient.update(requestorId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findById(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                           @PathVariable Integer bookingId) {
        log.info("requestorId={}, GET /bookings/{}", requestorId, bookingId);
        return bookingClient.findById(requestorId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> findByBookerAndState(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                                       @RequestParam(defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                       @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, name = "from") @Min(0) Integer offset,
                                                       @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId={}, GET /bookings/?state={}", requestorId, state);
        return bookingClient.findByBookerAndState(requestorId, state, offset, pageSize);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findByOwnerAndState(@RequestHeader(AppConfiguration.HEADER_USER_ID) Integer requestorId,
                                                      @RequestParam(defaultValue = "ALL") @ValueOfEnumConstraint(enumClass = BookingState.class) String state,
                                                      @RequestParam(defaultValue = AppConfiguration.OFFSET_DEFAULT, name = "from") @Min(0) Integer offset,
                                                      @RequestParam(defaultValue = AppConfiguration.PAGE_SIZE_MAX, name = "size") @Min(1) Integer pageSize) {
        log.info("requestorId, GET /bookings/owner?state={} requestorId={}", requestorId, state);
        return bookingClient.findByOwnerAndState(requestorId, state, offset, pageSize);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorMessage handleException(Exception exception) {
        return new ErrorMessage(exception.getMessage(), "Unknown state: UNSUPPORTED_STATUS");
    }
}
