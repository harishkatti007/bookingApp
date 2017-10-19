package com.mycompany.bookingapp.service.dto;

import com.mycompany.bookingapp.domain.SeatRows;
import com.mycompany.bookingapp.domain.Seating;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tech on 17/10/17.
 */
public class SeatMatrixDTO implements Serializable {

    Map<String, List<SeatingResponseDTO>> seatMatrix = new HashMap<>();

    public Map<String, List<SeatingResponseDTO>> getSeatMatrix() {
        return seatMatrix;
    }

    public void setSeatMatrix(Map<String, List<SeatingResponseDTO>> seatMatrix) {
        this.seatMatrix = seatMatrix;
    }

    @Override
    public String toString() {
        return "SeatMatrixDTO{" +
            "seatMatrix=" + seatMatrix +
            '}';
    }
}
