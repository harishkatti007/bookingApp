package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Screens.
 */
@Entity
@Table(name = "screens")
public class Screens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "screen_code", nullable = false)
    private String screenCode;

    @NotNull
    @Column(name = "screen_name", nullable = false)
    private String screenName;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @ManyToOne(optional = false)
    @NotNull
    private Venues venues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenCode() {
        return screenCode;
    }

    public Screens screenCode(String screenCode) {
        this.screenCode = screenCode;
        return this;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getScreenName() {
        return screenName;
    }

    public Screens screenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Screens capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Venues getVenues() {
        return venues;
    }

    public Screens venues(Venues venues) {
        this.venues = venues;
        return this;
    }

    public void setVenues(Venues venues) {
        this.venues = venues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screens screens = (Screens) o;
        if (screens.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screens.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Screens{" +
            "id=" + getId() +
            ", screenCode='" + getScreenCode() + "'" +
            ", screenName='" + getScreenName() + "'" +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
}
