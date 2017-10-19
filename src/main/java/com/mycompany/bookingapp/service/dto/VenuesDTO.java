package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Venues entity.
 */
public class VenuesDTO implements Serializable {

    private Long id;

    @NotNull
    private String venueCode;

    @NotNull
    private String venueName;

    private Long regionsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getRegionsId() {
        return regionsId;
    }

    public void setRegionsId(Long regionsId) {
        this.regionsId = regionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VenuesDTO venuesDTO = (VenuesDTO) o;
        if(venuesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), venuesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VenuesDTO{" +
            "id=" + getId() +
            ", venueCode='" + getVenueCode() + "'" +
            ", venueName='" + getVenueName() + "'" +
            "}";
    }
}
