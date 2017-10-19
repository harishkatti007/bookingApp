package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SeatRows entity.
 */
public class SeatRowsDTO implements Serializable {

    private Long id;

    @NotNull
    private String rowCode;

    @NotNull
    private String rowName;

    private Long screeningId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRowCode() {
        return rowCode;
    }

    public void setRowCode(String rowCode) {
        this.rowCode = rowCode;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeatRowsDTO seatRowsDTO = (SeatRowsDTO) o;
        if(seatRowsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatRowsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatRowsDTO{" +
            "id=" + getId() +
            ", rowCode='" + getRowCode() + "'" +
            ", rowName='" + getRowName() + "'" +
            "}";
    }
}
