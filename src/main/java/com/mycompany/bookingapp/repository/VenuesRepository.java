package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Venues;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Venues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VenuesRepository extends JpaRepository<Venues,Long> {
    
}
