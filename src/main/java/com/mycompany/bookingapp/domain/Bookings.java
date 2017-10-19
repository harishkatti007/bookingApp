package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mycompany.bookingapp.domain.enumeration.BookingStatus;

/**
 * A Bookings.
 */
@Entity
@Table(name = "bookings")
public class Bookings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "booking_date", nullable = false)
    private ZonedDateTime bookingDate;

    @NotNull
    @Column(name = "total_amount", precision=10, scale=2, nullable = false)
    private BigDecimal totalAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @NotNull
    @Column(name = "seat_ids", nullable = false)
    private String seatIds;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "userAccount_id")
    private UserAccount userAccount;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "movies_id")
    private Movies movies;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBookingDate() {
        return bookingDate;
    }

    public Bookings bookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(ZonedDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Bookings totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Bookings bookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
        return this;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getSeatIds() {
        return seatIds;
    }

    public Bookings seatIds(String seatIds) {
        this.seatIds = seatIds;
        return this;
    }

    public void setSeatIds(String seatIds) {
        this.seatIds = seatIds;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Bookings userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Movies getMovies() {
        return movies;
    }

    public Bookings movies(Movies movies) {
        this.movies = movies;
        return this;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    public Screening getScreening() {
        return screening;
    }

    public Bookings screening(Screening screening) {
        this.screening = screening;
        return this;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bookings bookings = (Bookings) o;
        if (bookings.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookings.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bookings{" +
            "id=" + getId() +
            ", bookingDate='" + getBookingDate() + "'" +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", bookingStatus='" + getBookingStatus() + "'" +
            ", seatIds='" + getSeatIds() + "'" +
            "}";
    }
}
