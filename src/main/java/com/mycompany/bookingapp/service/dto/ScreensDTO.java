package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Screens entity.
 */
public class ScreensDTO implements Serializable {

    private Long id;

    @NotNull
    private String screenCode;

    @NotNull
    private String screenName;

    @NotNull
    private Integer capacity;

    private Long venuesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenCode() {
        return screenCode;
    }

    public void setScreenCode(String screenCode) {
        this.screenCode = screenCode;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getVenuesId() {
        return venuesId;
    }

    public void setVenuesId(Long venuesId) {
        this.venuesId = venuesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScreensDTO screensDTO = (ScreensDTO) o;
        if(screensDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screensDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScreensDTO{" +
            "id=" + getId() +
            ", screenCode='" + getScreenCode() + "'" +
            ", screenName='" + getScreenName() + "'" +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
}
