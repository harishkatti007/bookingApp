package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Screening.
 */
@Entity
@Table(name = "screening")
public class Screening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "screening_date", nullable = false)
    private LocalDate screeningDate;

    @NotNull
    @Column(name = "screening_time", nullable = false)
    private ZonedDateTime screeningTime;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "screens_id")
    private Screens screens;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "movies_id")
    private Movies movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getScreeningDate() {
        return screeningDate;
    }

    public Screening screeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
        return this;
    }

    public void setScreeningDate(LocalDate screeningDate) {
        this.screeningDate = screeningDate;
    }

    public ZonedDateTime getScreeningTime() {
        return screeningTime;
    }

    public Screening screeningTime(ZonedDateTime screeningTime) {
        this.screeningTime = screeningTime;
        return this;
    }

    public void setScreeningTime(ZonedDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Screens getScreens() {
        return screens;
    }

    public Screening screens(Screens screens) {
        this.screens = screens;
        return this;
    }

    public void setScreens(Screens screens) {
        this.screens = screens;
    }

    public Movies getMovies() {
        return movies;
    }

    public Screening movies(Movies movies) {
        this.movies = movies;
        return this;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screening screening = (Screening) o;
        if (screening.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screening.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Screening{" +
            "id=" + getId() +
            ", screeningDate='" + getScreeningDate() + "'" +
            ", screeningTime='" + getScreeningTime() + "'" +
            "}";
    }
}
