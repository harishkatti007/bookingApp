package com.mycompany.bookingapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Movies.
 */
@Entity
@Table(name = "movies")
public class Movies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "movie_code", nullable = false)
    private String movieCode;

    @NotNull
    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "language", nullable = false)
    private String language;

    @ManyToOne(optional = false)
    @NotNull
    private MovieDimensions movieDimensions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieCode() {
        return movieCode;
    }

    public Movies movieCode(String movieCode) {
        this.movieCode = movieCode;
        return this;
    }

    public void setMovieCode(String movieCode) {
        this.movieCode = movieCode;
    }

    public String getMovieName() {
        return movieName;
    }

    public Movies movieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCategory() {
        return category;
    }

    public Movies category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public Movies language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MovieDimensions getMovieDimensions() {
        return movieDimensions;
    }

    public Movies movieDimensions(MovieDimensions movieDimensions) {
        this.movieDimensions = movieDimensions;
        return this;
    }

    public void setMovieDimensions(MovieDimensions movieDimensions) {
        this.movieDimensions = movieDimensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movies movies = (Movies) o;
        if (movies.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movies.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movies{" +
            "id=" + getId() +
            ", movieCode='" + getMovieCode() + "'" +
            ", movieName='" + getMovieName() + "'" +
            ", category='" + getCategory() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
