package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixRequest;

/**
 * Created by tech on 17/10/17.
 */
public interface CustomSeatMatrixService {

    SeatMatrixDTO getSeatMatrix(ScreeningDTO screeningDTO);
}
