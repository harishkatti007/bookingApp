package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.domain.Screens;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Screening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long> {

    Screening findByScreeningTimeAndMoviesAndScreens(ZonedDateTime dateTime, Movies movies, Screens screens);

    Screening findById(Long id);

    Screening findByScreeningTimeAndMovies_idAndScreens_id(ZonedDateTime dateTime, Long id, Long id1);

    List<Screening> findByScreeningTime(ZonedDateTime dateTime);
}
