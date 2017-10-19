package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Venues.
 */
@Entity
@Table(name = "venues")
public class Venues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "venue_code", nullable = false)
    private String venueCode;

    @NotNull
    @Column(name = "venue_name", nullable = false)
    private String venueName;

    @ManyToOne(optional = false)
    @NotNull
    private Regions regions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public Venues venueCode(String venueCode) {
        this.venueCode = venueCode;
        return this;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getVenueName() {
        return venueName;
    }

    public Venues venueName(String venueName) {
        this.venueName = venueName;
        return this;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Regions getRegions() {
        return regions;
    }

    public Venues regions(Regions regions) {
        this.regions = regions;
        return this;
    }

    public void setRegions(Regions regions) {
        this.regions = regions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Venues venues = (Venues) o;
        if (venues.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), venues.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Venues{" +
            "id=" + getId() +
            ", venueCode='" + getVenueCode() + "'" +
            ", venueName='" + getVenueName() + "'" +
            "}";
    }
}
