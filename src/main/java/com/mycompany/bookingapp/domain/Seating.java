package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Seating.
 */
@Entity
@Table(name = "seating")
public class Seating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "seat_code", nullable = false)
    private String seatCode;

    @NotNull
    @Column(name = "seat_name", nullable = false)
    private String seatName;

    @NotNull
    @Column(name = "seat_price", precision=10, scale=2, nullable = false)
    private BigDecimal seatPrice;

    @NotNull
    @Column(name = "booked", nullable = false)
    private Boolean booked;

    @ManyToOne(optional = false)
    @NotNull
    private SeatRows seatRows;

    @ManyToOne(optional = false)
    @NotNull
    private SeatType seatType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public Seating seatCode(String seatCode) {
        this.seatCode = seatCode;
        return this;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public Seating seatName(String seatName) {
        this.seatName = seatName;
        return this;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public BigDecimal getSeatPrice() {
        return seatPrice;
    }

    public Seating seatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
        return this;
    }

    public void setSeatPrice(BigDecimal seatPrice) {
        this.seatPrice = seatPrice;
    }

    public Boolean isBooked() {
        return booked;
    }

    public Seating booked(Boolean booked) {
        this.booked = booked;
        return this;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public SeatRows getSeatRows() {
        return seatRows;
    }

    public Seating seatRows(SeatRows seatRows) {
        this.seatRows = seatRows;
        return this;
    }

    public void setSeatRows(SeatRows seatRows) {
        this.seatRows = seatRows;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public Seating seatType(SeatType seatType) {
        this.seatType = seatType;
        return this;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seating seating = (Seating) o;
        if (seating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seating{" +
            "id=" + getId() +
            ", seatCode='" + getSeatCode() + "'" +
            ", seatName='" + getSeatName() + "'" +
            ", seatPrice='" + getSeatPrice() + "'" +
            ", booked='" + isBooked() + "'" +
            "}";
    }
}
