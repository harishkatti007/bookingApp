package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Movies;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Movies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoviesRepository extends JpaRepository<Movies,Long> {

    Movies findById(Long moviesId);
}
