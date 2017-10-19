package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.dao.ScreeningDao;
import com.mycompany.bookingapp.dao.SeatMatrixDao;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.domain.Seating;
import com.mycompany.bookingapp.service.CustomSeatMatrixService;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixRequest;
import com.mycompany.bookingapp.service.dto.SeatingResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tech on 17/10/17.
 */
@Service
public class CustomSeatMatrixServiceImpl implements CustomSeatMatrixService {

    private final Logger log = LoggerFactory.getLogger(CustomSeatMatrixServiceImpl.class);

    @Inject
    private SeatMatrixDao seatMatrixDao;

    @Inject
    private ScreeningDao screeningDao;

    @Override
    public SeatMatrixDTO getSeatMatrix(ScreeningDTO screeningDTO) {
        Screening screening = screeningDao.getScreeningDetails(screeningDTO);
        List<SeatRows> seatRows = seatMatrixDao.getSeatRows(screening);
        SeatMatrixDTO seatMatrixDTO = new SeatMatrixDTO();
        Map<String, List<SeatingResponseDTO>> seatMatrix = new HashMap<>();
        for(SeatRows row: seatRows) {
            List<Seating> seatings = seatMatrixDao.getSeatings(row);
            List<SeatingResponseDTO> seatingResponseDTOS = new ArrayList<>();
            for(Seating seating: seatings) {
                SeatingResponseDTO seatingResponseDTO = new SeatingResponseDTO();
                seatingResponseDTO.setBooked(seating.isBooked());
                seatingResponseDTO.setSeatCode(seating.getSeatCode());
                seatingResponseDTO.setSeatName(seating.getSeatName());
                seatingResponseDTO.setSeatPrice(seating.getSeatPrice());
                seatingResponseDTO.setSeatType(seating.getSeatType().getSeatTypeCode());
                seatingResponseDTO.setSeatingId(seating.getId());
                seatingResponseDTOS.add(seatingResponseDTO);
            }
            seatMatrix.put(row.getRowCode(), seatingResponseDTOS);
        }
        seatMatrixDTO.setSeatMatrix(seatMatrix);
        return seatMatrixDTO;
    }
}
