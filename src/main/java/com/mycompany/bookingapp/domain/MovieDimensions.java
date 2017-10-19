package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MovieDimensions.
 */
@Entity
@Table(name = "movie_dimensions")
public class MovieDimensions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dimension_code", nullable = false)
    private String dimensionCode;

    @NotNull
    @Column(name = "dimension_name", nullable = false)
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

    public MovieDimensions dimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
        return this;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public MovieDimensions dimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
        return this;
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
        MovieDimensions movieDimensions = (MovieDimensions) o;
        if (movieDimensions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieDimensions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieDimensions{" +
            "id=" + getId() +
            ", dimensionCode='" + getDimensionCode() + "'" +
            ", dimensionName='" + getDimensionName() + "'" +
            "}";
    }
}
