package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seating entity.
 */
public class SeatingDTO implements Serializable {

    private Long id;

    @NotNull
    private String seatCode;

    @NotNull
    private String seatName;

    @NotNull
    private BigDecimal seatPrice;

    @NotNull
    private Boolean booked;

    private Long seatRowsId;

    private Long seatTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public Long getSeatRowsId() {
        return seatRowsId;
    }

    public void setSeatRowsId(Long seatRowsId) {
        this.seatRowsId = seatRowsId;
    }

    public Long getSeatTypeId() {
        return seatTypeId;
    }

    public void setSeatTypeId(Long seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeatingDTO seatingDTO = (SeatingDTO) o;
        if(seatingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatingDTO{" +
            "id=" + getId() +
            ", seatCode='" + getSeatCode() + "'" +
            ", seatName='" + getSeatName() + "'" +
            ", seatPrice='" + getSeatPrice() + "'" +
            ", booked='" + isBooked() + "'" +
            "}";
    }
}
