package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.domain.enumeration.BookingStatus;
import com.mycompany.bookingapp.repository.BookingsRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by tech on 19/10/17.
 */
@Service
public class BookingDao {

    @Inject
    private BookingsRepository bookingsRepository;

    @Inject
    private SeatingDao seatingDao;

    @Inject
    private MoviesDao moviesDao;

    @Inject
    private ScreeningDao screeningDao;

    @Inject
    private UserAccountDao userAccountDao;

    @Transactional
    public Bookings bookSeats(UserAccount userAccount, List<Long> seatIds, BigDecimal totalAmount, Long movieId, Long screeingId) {
        int noOfSeats = seatIds.size();
        int bookedSeats = seatingDao.bookSeatings(seatIds);
        if(noOfSeats != bookedSeats) {
            throw new RuntimeException();
        }
        Movies movies = moviesDao.getMoviesById(movieId);
        Screening screening = screeningDao.findById(screeingId);
        UserAccount userAccount1 = userAccountDao.getUserAccountDetails(userAccount);
        Bookings bookings = new Bookings();
        bookings.setBookingDate(ZonedDateTime.now());
        bookings.setBookingStatus(BookingStatus.CONFIRMED);
        bookings.setMovies(movies);
        bookings.setScreening(screening);
        bookings.setSeatIds(StringUtils.join(seatIds, ","));
        bookings.setUserAccount(userAccount1);
        bookings.setTotalAmount(totalAmount);
        Bookings booked = bookingsRepository.save(bookings);
        return booked;
    }

    public Bookings findById(Long bookingId) {
        return bookingsRepository.findOne(bookingId);
    }

    public Bookings save(Bookings booking) {
        return bookingsRepository.save(booking);
    }
}
