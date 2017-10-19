package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.MovieDimensions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MovieDimensions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovieDimensionsRepository extends JpaRepository<MovieDimensions,Long> {
    
}
