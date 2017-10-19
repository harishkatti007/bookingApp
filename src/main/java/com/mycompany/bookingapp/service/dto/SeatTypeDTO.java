package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SeatType entity.
 */
public class SeatTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String seatTypeCode;

    @NotNull
    private String seatTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatTypeCode() {
        return seatTypeCode;
    }

    public void setSeatTypeCode(String seatTypeCode) {
        this.seatTypeCode = seatTypeCode;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeatTypeDTO seatTypeDTO = (SeatTypeDTO) o;
        if(seatTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatTypeDTO{" +
            "id=" + getId() +
            ", seatTypeCode='" + getSeatTypeCode() + "'" +
            ", seatTypeName='" + getSeatTypeName() + "'" +
            "}";
    }
}
