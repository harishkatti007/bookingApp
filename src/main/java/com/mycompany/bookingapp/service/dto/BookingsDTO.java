package com.mycompany.bookingapp.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.mycompany.bookingapp.domain.enumeration.BookingStatus;

/**
 * A DTO for the Bookings entity.
 */
public class BookingsDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime bookingDate;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private BookingStatus bookingStatus;

    @NotNull
    private String seatIds;

    private Long userAccountId;

    private Long moviesId;

    private Long screeningId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(String seatIds) {
        this.seatIds = seatIds;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Long getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(Long moviesId) {
        this.moviesId = moviesId;
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

        BookingsDTO bookingsDTO = (BookingsDTO) o;
        if(bookingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookingsDTO{" +
            "id=" + getId() +
            ", bookingDate='" + getBookingDate() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", bookingStatus='" + getBookingStatus() + "'" +
            ", seatIds='" + getSeatIds() + "'" +
            "}";
    }
}
