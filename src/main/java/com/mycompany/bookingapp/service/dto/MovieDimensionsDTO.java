package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MovieDimensions entity.
 */
public class MovieDimensionsDTO implements Serializable {

    private Long id;

    @NotNull
    private String dimensionCode;

    @NotNull
    private String dimensionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDimensionCode() {
        return dimensionCode;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieDimensionsDTO movieDimensionsDTO = (MovieDimensionsDTO) o;
        if(movieDimensionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieDimensionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieDimensionsDTO{" +
            "id=" + getId() +
            ", dimensionCode='" + getDimensionCode() + "'" +
            ", dimensionName='" + getDimensionName() + "'" +
            "}";
    }
}
