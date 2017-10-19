package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Regions.
 */
@Entity
@Table(name = "regions")
public class Regions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "region_code", nullable = false)
    private String regionCode;

    @NotNull
    @Column(name = "region_name", nullable = false)
    private String regionName;

    @NotNull
    @Column(name = "pincode", nullable = false)
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

    public Regions regionCode(String regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public Regions regionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getPincode() {
        return pincode;
    }

    public Regions pincode(Long pincode) {
        this.pincode = pincode;
        return this;
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
        Regions regions = (Regions) o;
        if (regions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Regions{" +
            "id=" + getId() +
            ", regionCode='" + getRegionCode() + "'" +
            ", regionName='" + getRegionName() + "'" +
            ", pincode='" + getPincode() + "'" +
            "}";
    }
}
