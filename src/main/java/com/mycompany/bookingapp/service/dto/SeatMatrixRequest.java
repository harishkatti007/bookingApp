package com.mycompany.bookingapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by tech on 17/10/17.
 */
public class SeatMatrixRequest implements Serializable {

    private Long moviesId;
    private Long screenId;
    private ZonedDateTime dateTime;

    public Long getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(Long moviesId) {
        this.moviesId = moviesId;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SeatMatrixRequest{" +
            "moviesId=" + moviesId +
            ", screenId=" + screenId +
            ", dateTime=" + dateTime +
            '}';
    }
}
