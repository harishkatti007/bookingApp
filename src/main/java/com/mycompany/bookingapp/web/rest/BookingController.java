package com.mycompany.bookingapp.web.rest;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.mycompany.bookingapp.service.CustomBookingService;
import com.mycompany.bookingapp.service.dto.BookingRequestDto;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.dto.SeatingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by tech on 13/10/17.
 */
@RestController
@RequestMapping("/api")
public class BookingController {

    private final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Inject
    private CustomBookingService customBookingService;

    @RequestMapping(value = "/bookSeats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingsDTO> makeBookings(@RequestBody @Valid BookingRequestDto bookingRequestDto) {
        if(bookingRequestDto==null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(customBookingService.bookSeats(bookingRequestDto.getAccount(), bookingRequestDto.getSeatingIds(),
            bookingRequestDto.getTotalAmount(), bookingRequestDto.getMovieId(), bookingRequestDto.getScreeningId()));
    }

    @RequestMapping(value = "/cancelBooking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingsDTO> cancelBookings(@RequestParam(value = "bookingId", required = true) Long bookingId) {

        return ResponseEntity.ok(customBookingService.cancelBooking(bookingId));
    }
}
