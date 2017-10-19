package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SeatType.
 */
@Entity
@Table(name = "seat_type")
public class SeatType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "seat_type_code", nullable = false)
    private String seatTypeCode;

    @NotNull
    @Column(name = "seat_type_name", nullable = false)
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

    public SeatType seatTypeCode(String seatTypeCode) {
        this.seatTypeCode = seatTypeCode;
        return this;
    }

    public void setSeatTypeCode(String seatTypeCode) {
        this.seatTypeCode = seatTypeCode;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public SeatType seatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
        return this;
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
        SeatType seatType = (SeatType) o;
        if (seatType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatType{" +
            "id=" + getId() +
            ", seatTypeCode='" + getSeatTypeCode() + "'" +
            ", seatTypeName='" + getSeatTypeName() + "'" +
            "}";
    }
}
