package com.mycompany.bookingapp.web.rest;

import com.mycompany.bookingapp.service.CustomSeatMatrixService;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by tech on 17/10/17.
 */
@RestController
@RequestMapping("/api")
public class SeatMatrixController {

    private final Logger log = LoggerFactory.getLogger(SeatMatrixController.class);

    @Inject
    private CustomSeatMatrixService customSeatMatrixService;

    @RequestMapping(value = "/getSeatMatrix", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeatMatrixDTO> getSeatMatrix(@RequestBody @Valid ScreeningDTO screeningDTO) {
        if(screeningDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        log.debug("seatMatrixRequest: {}", screeningDTO.toString());
        return ResponseEntity.ok(customSeatMatrixService.getSeatMatrix(screeningDTO));
    }
}
