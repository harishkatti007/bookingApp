package com.mycompany.bookingapp.service.dto;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Screening entity.
 */
public class ScreeningDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate screeningDate;

    @NotNull
    private ZonedDateTime screeningTime;

    private Long screensId;

    private Long moviesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public ZonedDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(ZonedDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Long getScreensId() {
        return screensId;
    }

    public void setScreensId(Long screensId) {
        this.screensId = screensId;
    }

    public Long getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(Long moviesId) {
        this.moviesId = moviesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScreeningDTO screeningDTO = (ScreeningDTO) o;
        if(screeningDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screeningDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScreeningDTO{" +
            "id=" + getId() +
            ", screeningDate='" + getScreeningDate() + "'" +
            ", screeningTime='" + getScreeningTime() + "'" +
            "}";
    }
}
