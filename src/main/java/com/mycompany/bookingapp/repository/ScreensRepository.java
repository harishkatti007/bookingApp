package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Screens;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Screens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScreensRepository extends JpaRepository<Screens,Long> {

    Screens findById(Long screenId);
}
