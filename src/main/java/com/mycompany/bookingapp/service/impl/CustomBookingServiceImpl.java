package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.dao.BookingDao;
import com.mycompany.bookingapp.dao.SeatingDao;
import com.mycompany.bookingapp.domain.Bookings;
import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.domain.UserAccount;
import com.mycompany.bookingapp.domain.enumeration.BookingStatus;
import com.mycompany.bookingapp.service.CustomBookingService;
import com.mycompany.bookingapp.service.CustomUserAccountService;
import com.mycompany.bookingapp.service.dto.BookingsDTO;
import com.mycompany.bookingapp.service.dto.SeatingResponseDTO;
import com.mycompany.bookingapp.service.mapper.BookingsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tech on 18/10/17.
 */
@Service
public class CustomBookingServiceImpl implements CustomBookingService {

    @Inject
    private CustomUserAccountService customUserAccountService;

    @Inject
    private SeatingDao seatingDao;

    @Inject
    private BookingDao bookingDao;

    @Inject
    private BookingsMapper bookingsMapper;

    @Override
    public BookingsDTO bookSeats(UserAccount account, List<Long> seatingIds, BigDecimal totalAmount, Long movieId, Long screeningId) {
        boolean userAccountValid = customUserAccountService.validateUserAccount(account);
        List<Seating> seatings = seatingDao.getSeatDetails(seatingIds);
        boolean amountValidate = validateTotalAmount(seatings, totalAmount);
        boolean seatsBooked = false;
        Bookings bookedSeats = null;
        if(userAccountValid && amountValidate) {
            bookedSeats = bookingDao.bookSeats(account, seatingIds, totalAmount, movieId, screeningId);
        } else {
            //throw new RuntimeException();
        }
        return bookingsMapper.toDto(bookedSeats);
    }

    private boolean validateTotalAmount(List<Seating> seatings, BigDecimal totalAmount) {
        BigDecimal amount = new BigDecimal(0);
        for(Seating seating: seatings) {
            amount = amount.add(seating.getSeatPrice());
        }
        return totalAmount.compareTo(amount)==1?false:true;
    }

    @Override
    public BookingsDTO cancelBooking(Long bookingId) {
        Bookings booking = bookingDao.findById(bookingId);
        if(booking==null) {
            // throw exception
        }
        String seatIds = booking.getSeatIds();
        int updatedRows = seatingDao.cancelBookings(seatIds);
        if(updatedRows > 0) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
        }
        Bookings cancelled = bookingDao.save(booking);
        return bookingsMapper.toDto(cancelled);
    }


}
