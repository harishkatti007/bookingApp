package com.mycompany.bookingapp.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tech on 18/10/17.
 */
public class SeatingResponseDTO implements Serializable{

    private Long seatingId;
    private String seatCode;
    private String seatName;
    private BigDecimal seatPrice;
    private Boolean booked;
    private String seatType;

    public Long getSeatingId() {
        return seatingId;
    }

    public void setSeatingId(Long seatingId) {
        this.seatingId = seatingId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
