package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Regions entity.
 */
public class RegionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String regionCode;

    @NotNull
    private String regionName;

    @NotNull
    private Long pincode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegionsDTO regionsDTO = (RegionsDTO) o;
        if(regionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegionsDTO{" +
            "id=" + getId() +
            ", regionCode='" + getRegionCode() + "'" +
            ", regionName='" + getRegionName() + "'" +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
