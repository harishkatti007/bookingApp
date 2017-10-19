package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.domain.Bookings;
import com.mycompany.bookingapp.domain.UserAccount;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixRequest;
import com.mycompany.bookingapp.service.dto.SeatingResponseDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tech on 17/10/17.
 */
public interface CustomBookingService {

    BookingsDTO bookSeats(UserAccount account, List<Long> seatingIds, BigDecimal totalAmount, Long movieId, Long screeningId);

    BookingsDTO cancelBooking(Long bookingId);
}
