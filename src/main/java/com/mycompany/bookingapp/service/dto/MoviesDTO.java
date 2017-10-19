package com.mycompany.bookingapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Movies entity.
 */
public class MoviesDTO implements Serializable {

    private Long id;

    @NotNull
    private String movieCode;

    @NotNull
    private String movieName;

    @NotNull
    private String category;

    @NotNull
    private String language;

    private Long movieDimensionsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getMovieDimensionsId() {
        return movieDimensionsId;
    }

    public void setMovieDimensionsId(Long movieDimensionsId) {
        this.movieDimensionsId = movieDimensionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoviesDTO moviesDTO = (MoviesDTO) o;
        if(moviesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moviesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoviesDTO{" +
            "id=" + getId() +
            ", movieCode='" + getMovieCode() + "'" +
            ", movieName='" + getMovieName() + "'" +
            ", category='" + getCategory() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
