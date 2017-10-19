package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.repository.SeatingRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 19/10/17.
 */
@Service
public class SeatingDao {

    @Inject
    private SeatingRepository seatingRepository;

    public List<Seating> getSeatDetails(List<Long> seatIds) {
        return seatingRepository.findByIdIn(seatIds);
    }

    @Transactional
    public int bookSeatings(List<Long> seatIds) {
        return seatingRepository.updateBookings(seatIds);
    }

    @Transactional
    public int cancelBookings(String seatIds) {
        String[] i = seatIds.split(",");
        List<Long> ids = new ArrayList<>();
        for(String s: i) {
            ids.add(Long.parseLong(s));
        }
        return seatingRepository.cancelBookings(ids);
    }
}
