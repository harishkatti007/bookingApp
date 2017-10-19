package com.mycompany.bookingapp.service.dto;

import com.mycompany.bookingapp.domain.UserAccount;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tech on 19/10/17.
 */
public class BookingRequestDto implements Serializable {

    @NotNull
    UserAccount account = new UserAccount();
    @NotNull
    List<Long> seatingIds = new ArrayList<>();
    @NotNull
    Long movieId;
    @NotNull
    Long screeningId;
    @NotNull
    @Min(1)
    BigDecimal totalAmount;

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public List<Long> getSeatingIds() {
        return seatingIds;
    }

    public void setSeatingIds(List<Long> seatingIds) {
        this.seatingIds = seatingIds;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
