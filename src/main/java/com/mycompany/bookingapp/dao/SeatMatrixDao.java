package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.repository.ScreeningRepository;
import com.mycompany.bookingapp.repository.SeatRowsRepository;
import com.mycompany.bookingapp.repository.SeatingRepository;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tech on 17/10/17.
 */
@Service
public class SeatMatrixDao {

    private final Logger log = LoggerFactory.getLogger(SeatMatrixDao.class);

    @Inject
    private SeatingRepository seatingRepository;

    @Inject
    private SeatRowsRepository seatRowsRepository;

    @Inject
    private ScreeningRepository screeningRepository;


    public List<SeatRows> getSeatRows(Screening screeningDTO) {
        return seatRowsRepository.findByScreening_Id(screeningDTO.getId());
    }

    public List<Seating> getSeatings(SeatRows seatRows) {
        return seatingRepository.
            findBySeatRows_Id(seatRows.getId());
    }
}
